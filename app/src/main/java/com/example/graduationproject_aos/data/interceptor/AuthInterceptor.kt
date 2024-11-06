package com.example.graduationproject_aos.data.interceptor

import android.content.Context
import android.util.Log
import com.example.graduationproject_aos.data.model.response.UserResponseToken
import com.example.graduationproject_aos.data.service.AuthService
import com.example.graduationproject_aos.domain.repository.DataStoreRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Provider

class AuthInterceptor @Inject constructor(
    @ApplicationContext private val context: Context,
    private val json: Json,
    private val dataStoreRepository: DataStoreRepository,
    private val authService: Provider<AuthService>
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        val accessToken = runBlocking(Dispatchers.IO) { getAccessToken() }
        if (accessToken.isEmpty()) {
            Log.e("ABCD", "Access Token이 존재하지 않습니다.")
            return chain.proceed(originalRequest)
        }

        val headerRequest = originalRequest.newAuthBuilder(accessToken).build()
        logRequestHeaders(headerRequest)
        var response = chain.proceed(headerRequest)

        if (response.code == CODE_TOKEN_EXPIRED) {
            response.close()
            Log.e("ABCD", "액세스 토큰 만료, 토큰 재발급 시도 중...")
            response = runBlocking { handleTokenExpired(chain, originalRequest) }
        } else if (response.code == CODE_INVALID_USER) {
            Log.e("ABCD", "잘못된 사용자 인증입니다.")
            saveAccessToken("", "")
        }

        return response
    }

    private fun logRequestHeaders(request: Request) {
        Log.d("AuthInterceptor", "Request URL: ${request.url}")
        request.headers.forEach { header ->
            Log.d("AuthInterceptor", "Header: ${header.first} = ${header.second}")
        }
    }

    private fun Request.newAuthBuilder(token: String) =
        this.newBuilder().header("Authorization", "Bearer $token") // 덮어쓰기로 중복 방지

    private suspend fun getAccessToken(): String {
        return withContext(Dispatchers.IO) { dataStoreRepository.getAccessToken().first() ?: "" }
    }

    private suspend fun getRefreshToken(): String {
        return withContext(Dispatchers.IO) { dataStoreRepository.getRefreshToken().first() ?: "" }
    }

    private fun saveAccessToken(accessToken: String, refreshToken: String) = runBlocking {
        Log.d("AuthInterceptor", "저장하는 액세스 토큰: $accessToken, 리프레시 토큰: $refreshToken")
        dataStoreRepository.saveAccessToken(accessToken, refreshToken)
        Log.d("AuthInterceptor", "토큰 저장 완료")
    }

    private suspend fun handleTokenExpired(
        chain: Interceptor.Chain,
        originalRequest: Request
    ): Response {
        return try {
            // 요청으로부터 새로운 토큰 가져오기
            val refreshTokenResponse = authService.get().postFreshToken(
                UserResponseToken(
                    accessToken = getAccessToken(),
                    refreshToken = getRefreshToken()
                )
            )

            val newAccessToken = refreshTokenResponse.data.accessToken
            val newRefreshToken = refreshTokenResponse.data.refreshToken

            Log.d("ABCD", "새로운 액세스 토큰: $newAccessToken")

            // 새로운 토큰 저장
            saveAccessToken(newAccessToken, newRefreshToken)

            // 새로운 요청으로 토큰 업데이트 후 실행
            val newRequest = originalRequest.newAuthBuilder(newAccessToken).build()
            chain.proceed(newRequest)
        } catch (e: Exception) {
            Log.e("ABCD", "토큰 갱신 실패: ${e.message}")
            saveAccessToken("", "")
            throw e
        }
    }

    companion object {
        private const val CODE_TOKEN_EXPIRED = 401
        private const val CODE_INVALID_USER = 404
    }
}
