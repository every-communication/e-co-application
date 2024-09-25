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

    suspend fun getAllFriends() = authService.getAllFriends()

    suspend fun postFriendRequestedApprove(id: Int) = authService.postFriendRequestedApprove(id)

    suspend fun getFriendRequest() = authService.getFriendRequest()

    suspend fun postFriendRequest(id: String) = authService.postFriendRequest(id)

    suspend fun patchFriendRequestedRemove(id: Int) = authService.patchFriendRequestedRemove(id)

    suspend fun patchFriendRequestRemove(id: Int) = authService.patchFriendRequestRemove(id)

    suspend fun getFriendRequested() = authService.getFriendRequested()

}