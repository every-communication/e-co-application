package com.example.graduationproject_aos.data.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MyInfo(
    @SerialName("id")
    val id: Int,
    @SerialName("email")
    val email: String,
    @SerialName("nickname")
    val nickname: String?,
    @SerialName("thumbnail")
    val thumbnail: String?,
    @SerialName("userType")
    val userType: String?,
    @SerialName("socialType")
    val socialType: String?,
)

@Serializable
data class ResponseGetMyInfo(
    @SerialName("status")
    val status: Int,
    @SerialName("message")
    val message: String,
    @SerialName("data")
    val data: MyInfo
)