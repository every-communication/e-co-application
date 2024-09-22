package com.example.graduationproject_aos.domain.repository

import com.example.graduationproject_aos.data.model.request.RequestUserSignInDto
import com.example.graduationproject_aos.data.model.response.ResponseUserSignInDto

interface UserRepository {

    suspend fun postLoginUser(requestUserSignInDto: RequestUserSignInDto): Result<ResponseUserSignInDto>

}