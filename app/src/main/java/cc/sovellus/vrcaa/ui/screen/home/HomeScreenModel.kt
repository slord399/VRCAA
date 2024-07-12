package cc.sovellus.vrcaa.ui.screen.home

import android.content.Context
import android.content.Intent
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import cc.sovellus.vrcaa.api.vrchat.VRChatCache
import cc.sovellus.vrcaa.api.vrchat.VRChatCache.WorldCache
import cc.sovellus.vrcaa.api.vrchat.models.Friend
import cc.sovellus.vrcaa.manager.ApiManager.cache
import cc.sovellus.vrcaa.manager.FriendManager
import cc.sovellus.vrcaa.widgets.FriendWidgetReceiver
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeScreenModel(context: Context) : ScreenModel {

    private var friendsListFlow = MutableStateFlow(listOf<Friend>())
    var friendsList = friendsListFlow.asStateFlow()

    private var recentlyVisitedFlow = MutableStateFlow(listOf<WorldCache>())
    var recentlyVisited = recentlyVisitedFlow.asStateFlow()

    private val listener = object : FriendManager.FriendListener {
        override fun onUpdateFriends(friends: MutableList<Friend>) {
            friendsListFlow.value = friends.toList()
        }
    }

    private val cacheListener = object : VRChatCache.CacheListener {
        override fun recentlyVisitedUpdated(worlds: MutableList<WorldCache>) {
            recentlyVisitedFlow.value = worlds.toList()
        }

        override fun cacheUpdated() {
            val intent = Intent(context, FriendWidgetReceiver::class.java).apply { action = "FRIEND_LOCATION_UPDATE" }
            context.sendBroadcast(intent)
            fetchContent()
        }
    }

    init {
        FriendManager.addFriendListener(listener)
        cache.addCacheListener(cacheListener)
    }

    private fun fetchContent() {
        screenModelScope.launch {
            friendsListFlow.value = FriendManager.getFriends()
            recentlyVisitedFlow.value = cache.getRecentlyVisited()
        }
    }
}