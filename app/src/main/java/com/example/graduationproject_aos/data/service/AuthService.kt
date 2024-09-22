package com.example.graduationproject_aos.data.service

import com.example.graduationproject_aos.data.model.request.RequestUserSignInDto
import com.example.graduationproject_aos.data.model.response.ResponseUserSignInDto
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {

    @POST("auth/sign-in")
    suspend fun login(
        @Body request: RequestUserSignInDto
    ): ResponseUserSignInDto

}