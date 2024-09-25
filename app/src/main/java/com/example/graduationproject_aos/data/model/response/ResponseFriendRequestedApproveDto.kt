package com.example.graduationproject_aos.data.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FriendApproveResponse(
    @SerialName("userId")
    val userId: Int,
    @SerialName("friendId")
    val friendId: Int,
)

@Serializable
data class ResponseFriendRequestedApproveDto(
    @SerialName("status")
    val status: Int,
    @SerialName("message")
    val message: String,
    @SerialName("data")
    val data: FriendApproveResponse
)

