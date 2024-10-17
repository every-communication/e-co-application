package com.example.graduationproject_aos.data.service

import com.example.graduationproject_aos.data.model.request.RequestUserSignInDto
import com.example.graduationproject_aos.data.model.request.RequestUserSignUpDto
import com.example.graduationproject_aos.data.model.response.ResponseDto
import com.example.graduationproject_aos.data.model.response.ResponseFriendRequestedApproveDto
import com.example.graduationproject_aos.data.model.response.ResponseGetFriendList
import com.example.graduationproject_aos.data.model.response.ResponsePatchFriendRequestRemove
import com.example.graduationproject_aos.data.model.response.ResponsePostFriendRequest
import com.example.graduationproject_aos.data.model.response.ResponseSearchFriendList
import com.example.graduationproject_aos.data.model.response.ResponseUserSignInDto
import com.example.graduationproject_aos.data.model.response.UserResponseToken
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface AuthService {

    @POST("auth/sign-in")
    suspend fun login(
        @Body request: RequestUserSignInDto
    ): ResponseUserSignInDto

    @POST("auth/sign-up")
    suspend fun signup(
        @Body request: RequestUserSignUpDto
    ): ResponseDto

    @GET("/friends")
    suspend fun getAllFriends(): ResponseGetFriendList

    @POST("/friend-requested/approve")
    suspend fun postFriendRequestedApprove(
        @Body request: Int
    ): ResponseFriendRequestedApproveDto

    @GET("/friend-request")
    suspend fun getFriendRequest(): ResponseGetFriendList

    @POST("/friend-request/{friendId}")
    suspend fun postFriendRequest(
        @Path("friendId") friendId: Int
    ): ResponsePostFriendRequest

    @PATCH("/friend-requested/remove")
    suspend fun patchFriendRequestedRemove(
        @Body request: Int
    ): ResponsePatchFriendRequestRemove

    @PATCH("/friend-request/remove")
    suspend fun patchFriendRequestRemove(
        @Body request: Int
    ): ResponsePatchFriendRequestRemove

    @GET("/friend-requested")
    suspend fun getFriendRequested(): ResponseGetFriendList

    @POST("/auth/refresh")
    suspend fun postFreshToken(
        @Body request: UserResponseToken
    ): ResponseUserSignInDto

    @HTTP(method = "DELETE", path = "/friends/{friendId}", hasBody = false)
    suspend fun deleteFriend(
        @Path("friendId") friendId: Int,
    ): ResponseDto

    @GET("/friend-search")
    suspend fun getSearchFriend(
        @Query("userInfo") userInfo: String,
    ): ResponseSearchFriendList
}