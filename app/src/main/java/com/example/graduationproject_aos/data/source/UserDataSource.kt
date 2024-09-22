package com.example.graduationproject_aos.data.source

import com.example.graduationproject_aos.data.model.request.RequestUserSignInDto
import com.example.graduationproject_aos.data.model.request.RequestUserSignUpDto
import com.example.graduationproject_aos.data.service.AuthService
import javax.inject.Inject

class UserDataSource @Inject constructor(
    private val authService: AuthService
) {
    suspend fun postLoginUser(requestUserSignInDto: RequestUserSignInDto) = authService.login(requestUserSignInDto)

    suspend fun postSignUpUser(requestUserSignUpDto: RequestUserSignUpDto) = authService.signup(requestUserSignUpDto)

}