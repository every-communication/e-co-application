package com.example.graduationproject_aos.data.model.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RequestUserSignInDto(
    @SerialName("email")
    val email: String,
    @SerialName("password")
    val password: String,
)