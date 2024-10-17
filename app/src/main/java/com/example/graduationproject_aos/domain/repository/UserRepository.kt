package com.example.graduationproject_aos.domain.repository

import com.example.graduationproject_aos.data.model.request.RequestUserSignInDto
import com.example.graduationproject_aos.data.model.request.RequestUserSignUpDto
import com.example.graduationproject_aos.data.model.response.ResponseDto
import com.example.graduationproject_aos.data.model.response.ResponseFriendRequestedApproveDto
import com.example.graduationproject_aos.data.model.response.ResponseGetFriendList
import com.example.graduationproject_aos.data.model.response.ResponsePatchFriendRequestRemove
import com.example.graduationproject_aos.data.model.response.ResponsePostFriendRequest
import com.example.graduationproject_aos.data.model.response.ResponseSearchFriendList
import com.example.graduationproject_aos.data.model.response.ResponseUserSignInDto

interface UserRepository {

    suspend fun postLoginUser(requestUserSignInDto: RequestUserSignInDto): Result<ResponseUserSignInDto>

    suspend fun postSignUpUser(requestUserSignUpDto: RequestUserSignUpDto): Result<ResponseDto>

    suspend fun getAllFriends(): Result<ResponseGetFriendList>

    suspend fun postFriendRequestedApprove(id: Int): Result<ResponseFriendRequestedApproveDto>

    suspend fun getFriendRequest(): Result<ResponseGetFriendList>

    suspend fun postFriendRequest(id: Int): Result<ResponsePostFriendRequest>

    suspend fun patchFriendRequestedRemove(id: Int): Result<ResponsePatchFriendRequestRemove>

    suspend fun patchFriendRequestRemove(id: Int): Result<ResponsePatchFriendRequestRemove>

    suspend fun getFriendRequested(): Result<ResponseGetFriendList>

    suspend fun deleteFriend(id: Int): Result<ResponseDto>

    suspend fun searchFriend(userInfo: String): Result<ResponseSearchFriendList>

}