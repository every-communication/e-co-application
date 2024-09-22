package com.example.graduationproject_aos.data.repository

import com.example.graduationproject_aos.data.model.request.RequestUserSignInDto
import com.example.graduationproject_aos.data.model.response.ResponseUserSignInDto
import com.example.graduationproject_aos.data.source.UserDataSource
import com.example.graduationproject_aos.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userDataSource: UserDataSource,
) : UserRepository {
    override suspend fun postLoginUser(requestUserSignInDto: RequestUserSignInDto): Result<ResponseUserSignInDto> =
        runCatching {
            userDataSource.postLoginUser(requestUserSignInDto)
        }

}