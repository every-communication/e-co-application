package com.example.graduationproject_aos.data.repository

import com.example.graduationproject_aos.data.model.request.RequestUserSignInDto
import com.example.graduationproject_aos.data.model.request.RequestUserSignUpDto
import com.example.graduationproject_aos.data.model.response.ResponseDto
import com.example.graduationproject_aos.data.model.response.ResponseFriendRequestedApproveDto
import com.example.graduationproject_aos.data.model.response.ResponseGetFriendList
import com.example.graduationproject_aos.data.model.response.ResponsePatchFriendRequestRemove
import com.example.graduationproject_aos.data.model.response.ResponsePostFriendRequest
import com.example.graduationproject_aos.data.model.response.ResponseSearchFriendList
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

    override suspend fun postSignUpUser(requestUserSignUpDto: RequestUserSignUpDto): Result<ResponseDto> =
        runCatching {
            userDataSource.postSignUpUser(requestUserSignUpDto)
        }

    override suspend fun getAllFriends(): Result<ResponseGetFriendList> =
        runCatching {
            userDataSource.getAllFriends()
        }

    override suspend fun postFriendRequestedApprove(id: Int): Result<ResponseFriendRequestedApproveDto> =
        runCatching {
            userDataSource.postFriendRequestedApprove(id)
        }

    override suspend fun getFriendRequest(): Result<ResponseGetFriendList> =
        runCatching {
            userDataSource.getFriendRequest()
        }

    override suspend fun postFriendRequest(id: Int): Result<ResponsePostFriendRequest> =
        runCatching {
            userDataSource.postFriendRequest(id)
        }

    override suspend fun patchFriendRequestedRemove(id: Int): Result<ResponsePatchFriendRequestRemove> =
        runCatching {
            userDataSource.patchFriendRequestedRemove(id)
        }

    override suspend fun patchFriendRequestRemove(id: Int): Result<ResponsePatchFriendRequestRemove> =
        runCatching {
            userDataSource.patchFriendRequestRemove(id)
        }

    override suspend fun getFriendRequested(): Result<ResponseGetFriendList> =
        runCatching {
            userDataSource.getFriendRequested()
        }

    override suspend fun deleteFriend(id: Int): Result<ResponseDto> =
        runCatching {
            userDataSource.deleteFriend(id)
        }

    override suspend fun searchFriend(userInfo: String): Result<ResponseSearchFriendList> =
        runCatching {
            userDataSource.searchFriend(userInfo)
        }
}
