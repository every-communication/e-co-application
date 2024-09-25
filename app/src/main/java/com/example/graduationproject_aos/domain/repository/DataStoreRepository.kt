package com.example.graduationproject_aos.domain.repository

import androidx.datastore.preferences.core.Preferences
import kotlinx.coroutines.flow.Flow

interface DataStoreRepository {

    suspend fun saveAccessToken(accessToken: String = "", refreshToken: String = "")

    suspend fun saveUserId(userId: Int = 0)

    suspend fun getAccessToken(): Flow<String?>

    suspend fun getRefreshToken(): Flow<String?>

    suspend fun getUserId(): Flow<Int?>

    suspend fun clearDataStore()

    suspend fun getStringValue(key: Preferences.Key<String>): Flow<String?>
}