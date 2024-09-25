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

        val headerRequest = originalRequest.newAuthBuilder()
            .build()

        val response = chain.proceed(headerRequest)

        when (response.code) {
            CODE_TOKEN_EXPIRED -> {
                try {
                    Log.e("ABCD", "액세스 토큰 만료, 토큰 재발급 합니다.")
                    response.close()
                    return runBlocking { handleTokenExpired(chain, originalRequest) }
                } catch (t: Throwable) {
                    Log.e("ABCD", "예외발생 ${t.message}")
                    saveAccessToken("", "")
                }
            }

            CODE_INVALID_USER -> {
                saveAccessToken("", "")
            }
        }
        return response
    }

    private fun Request.newAuthBuilder() =
        this.newBuilder().addHeader(
            "Authorization",
            "Bearer ${runBlocking(Dispatchers.IO) { getAccessToken() }}"
        )

    private suspend fun getAccessToken(): String {
        return dataStoreRepository.getAccessToken().first() ?: ""
    }

    private suspend fun getRefreshToken(): String {
        return dataStoreRepository.getRefreshToken().first() ?: ""
    }

    private fun saveAccessToken(accessToken: String, refreshToken: String) =
        runBlocking {
            dataStoreRepository.saveAccessToken(accessToken, refreshToken)
        }

    private suspend fun handleTokenExpired(
        chain: Interceptor.Chain,
        originalRequest: Request,
    ): Response {
        val refreshTokenResponse = authService.get().postFreshToken(
            UserResponseToken(
                accessToken = getAccessToken(),
                refreshToken = getRefreshToken(),
            )
        )
        Log.d("ABCD", "리프레시 토큰 : ${refreshTokenResponse.data.refreshToken}")

        saveAccessToken(
            refreshTokenResponse.data.accessToken,
            refreshTokenResponse.data.refreshToken
        )
        Log.d("ABCD", "리프레시 토큰 : ${refreshTokenResponse.data.refreshToken}")

        val newRequest = originalRequest.newAuthBuilder().build()
        return chain.proceed(newRequest)
    }

    companion object {
        private const val CODE_TOKEN_EXPIRED = 401
        private const val CODE_INVALID_USER = 404
    }
}
