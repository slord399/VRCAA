package cc.sovellus.vrcaa.manager

import cc.sovellus.vrcaa.helper.StatusHelper
import java.time.LocalDateTime
import java.util.UUID


object FeedManager {

    @Volatile private var feedListener: FeedListener? = null
    @Volatile private var feedList: MutableList<Feed> = ArrayList()

    enum class FeedType {
        FRIEND_FEED_UNKNOWN,
        FRIEND_FEED_ONLINE,
        FRIEND_FEED_OFFLINE,
        FRIEND_FEED_LOCATION,
        FRIEND_FEED_STATUS,
        FRIEND_FEED_FRIEND_REQUEST,
        FRIEND_FEED_REMOVED,
        FRIEND_FEED_ADDED
    }

    data class Feed(
        val type: FeedType,
        var feedId: UUID = UUID.randomUUID(),
        var friendId: String = "",
        var friendName: String = "",
        var friendPictureUrl: String = "",
        var friendStatus: StatusHelper.Status = StatusHelper.Status.Offline,
        var travelDestination: String = "",
        var feedTimestamp: LocalDateTime = LocalDateTime.now()
    )

    interface FeedListener {
        fun onReceiveUpdate(list: MutableList<Feed>)
    }

    @Synchronized
    fun addFeed(feed: Feed) {
        synchronized(feedList) {
            feedList.add(feed)
        }
        feedListener?.onReceiveUpdate(feedList)
    }

    @Synchronized
    fun getFeed(): MutableList<Feed> {
        synchronized(feedList) {
            return feedList
        }
    }

    @Synchronized
    fun setFeedListener(listener: FeedListener) {
        synchronized(listener) {
            feedListener = listener
        }
    }
}