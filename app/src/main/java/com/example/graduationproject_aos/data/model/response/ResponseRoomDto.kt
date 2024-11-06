package com.example.graduationproject_aos.data.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseRoomDetailDto(
    @SerialName("id")
    val id: Int,
    @SerialName("code")
    val code: String,
    @SerialName("user1Id")
    val user1Id: Int?,
    @SerialName("user2Id")
    val user2Id: Int?,
    @SerialName("ownerId")
    val ownerId: Int?,
    @SerialName("friendId")
    val friendId: Int?,
    @SerialName("mic1")
    val mic1: Boolean,
    @SerialName("cam1")
    val cam1: Boolean,
    @SerialName("mic2")
    val mic2: Boolean,
    @SerialName("cam2")
    val cam2: Boolean,
    @SerialName("createdAt")
    val createdAt: String?,
    @SerialName("deletedAt")
    val deletedAt: String?,
)

@Serializable
data class ResponseRoomDto(
    @SerialName("status")
    val status: Int,
    @SerialName("message")
    val message: String,
    @SerialName("data")
    val data: ResponseRoomDetailDto,
)