package com.example.graduationproject_aos.domain.repository

import com.example.graduationproject_aos.data.model.request.RequestUserSignInDto
import com.example.graduationproject_aos.data.model.request.RequestUserSignUpDto
import com.example.graduationproject_aos.data.model.response.ResponseDto
import com.example.graduationproject_aos.data.model.response.ResponseFriendRequestedApproveDto
import com.example.graduationproject_aos.data.model.response.ResponseGetFriendRequest
import com.example.graduationproject_aos.data.model.response.ResponsePatchFriendRequestRemove
import com.example.graduationproject_aos.data.model.response.ResponsePostFriendRequest
import com.example.graduationproject_aos.data.model.response.ResponseUserSignInDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST

interface UserRepository {

    suspend fun postLoginUser(requestUserSignInDto: RequestUserSignInDto): Result<ResponseUserSignInDto>

    suspend fun postSignUpUser(requestUserSignUpDto: RequestUserSignUpDto): Result<ResponseDto>

    suspend fun getAllFriends(): Result<ResponseDto>

    suspend fun postFriendRequestedApprove(id: Int): Result<ResponseFriendRequestedApproveDto>

    suspend fun getFriendRequest(): Result<ResponseGetFriendRequest>

    suspend fun postFriendRequest(id: String): Result<ResponsePostFriendRequest>

    suspend fun patchFriendRequestedRemove(id: Int): Result<ResponsePatchFriendRequestRemove>

    suspend fun patchFriendRequestRemove(id: Int): Result<ResponsePatchFriendRequestRemove>

    suspend fun getFriendRequested(): Result<ResponseGetFriendRequest>

}