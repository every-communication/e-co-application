package com.example.graduationproject_aos.domain.repository

import com.example.graduationproject_aos.data.model.request.RequestUserSignInDto
import com.example.graduationproject_aos.data.model.request.RequestUserSignUpDto
import com.example.graduationproject_aos.data.model.response.ResponseDto
import com.example.graduationproject_aos.data.model.response.ResponseUserSignInDto

interface UserRepository {

    suspend fun postLoginUser(requestUserSignInDto: RequestUserSignInDto): Result<ResponseUserSignInDto>

    suspend fun postSignUpUser(requestUserSignUpDto: RequestUserSignUpDto): Result<ResponseDto>
}