package com.example.graduationproject_aos.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.graduationproject_aos.domain.repository.DataStoreRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

class DataStoreRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : DataStoreRepository {
    override suspend fun saveAccessToken(accessToken: String, refreshToken: String) {
        dataStore.edit {
            it[ACCESS_TOKEN] = accessToken
            it[REFRESH_TOKEN] = refreshToken
        }
    }

    override suspend fun saveUserId(userId: Int) {
        dataStore.edit {
            it[USER_ID] = userId
        }
    }

    override suspend fun getAccessToken(): Flow<String?> {
        return getStringValue(ACCESS_TOKEN)
    }

    override suspend fun getRefreshToken(): Flow<String?> {
        return getStringValue(REFRESH_TOKEN)
    }

    override suspend fun getUserId(): Flow<Int?> {
        return dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    exception.printStackTrace()
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }
            .map {
                it[USER_ID]
            }
    }

    override suspend fun clearDataStore() {
        dataStore.edit { it.clear() }
    }

    override suspend fun getStringValue(key: Preferences.Key<String>): Flow<String?> {
        return dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    exception.printStackTrace()
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }
            .map {
                it[key]
            }
    }

    companion object PreferencesKeys {
        private val ACCESS_TOKEN: Preferences.Key<String> = stringPreferencesKey("access_token")
        private val REFRESH_TOKEN: Preferences.Key<String> = stringPreferencesKey("refresh_token")
        private val USER_ID: Preferences.Key<Int> = intPreferencesKey("user_id")
    }
}