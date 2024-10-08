package cc.sovellus.vrcaa.api.vrchat.models.websocket


import cc.sovellus.vrcaa.api.vrchat.models.Friend
import com.google.gson.annotations.SerializedName

data class FriendActive(
    @SerializedName("user")
    val user: Friend,
    @SerializedName("userId")
    val userId: String
)