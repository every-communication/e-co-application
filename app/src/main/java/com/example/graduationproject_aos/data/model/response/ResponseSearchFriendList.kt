package com.example.graduationproject_aos.data.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FriendSearchList(
    @SerialName("userId")
    val userId: Int,
    @SerialName("email")
    val email: String,
    @SerialName("nickname")
    val nickname: String,
    @SerialName("thumbnail")
    val thumbnail: String?,
    @SerialName("friendType")
    val friendType: String?,
)

@Serializable
data class ResponseSearchFriendList(
    @SerialName("status")
    val status: Int,
    @SerialName("message")
    val message: String,
    @SerialName("data")
    val data: List<FriendSearchList>
)