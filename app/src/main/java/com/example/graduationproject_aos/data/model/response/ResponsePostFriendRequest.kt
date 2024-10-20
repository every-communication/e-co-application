package com.example.graduationproject_aos.data.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RequestState(
    @SerialName("userId")
    val userId: Int,
    @SerialName("friendId")
    val friendId: Int,
    @SerialName("friendState")
    val friendState: String,
)

@Serializable
data class ResponsePostFriendRequest(
    @SerialName("status")
    val status: Int,
    @SerialName("message")
    val message: String,
    @SerialName("data")
    val data: RequestState
)