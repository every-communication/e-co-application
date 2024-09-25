package com.example.graduationproject_aos.screen.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.graduationproject_aos.data.model.request.RequestUserSignInDto
import com.example.graduationproject_aos.data.model.response.ResponseUserSignInDto
import com.example.graduationproject_aos.domain.repository.DataStoreRepository
import com.example.graduationproject_aos.domain.repository.UserRepository
import com.example.graduationproject_aos.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val dataStoreRepository: DataStoreRepository
) : ViewModel() {

    private val _postLoginUserState =
        MutableStateFlow<UiState<ResponseUserSignInDto>>(UiState.Loading)
    val postLoginUserState: StateFlow<UiState<ResponseUserSignInDto>> =
        _postLoginUserState.asStateFlow()

    fun resetLoginState() {
        _postLoginUserState.value = UiState.Empty
    }
    fun postLoginUser(id: String, pw: String) {
        viewModelScope.launch {
            userRepository.postLoginUser(
                RequestUserSignInDto(
                    email = id,
                    password = pw
                )
            ).onSuccess { response ->
                _postLoginUserState.value = UiState.Success(response)
                saveAccessToken(response.data.accessToken, response.data.refreshToken)
                Timber.e("성공 $response")
            }.onFailure { t ->
                Log.e("ABCD", "ViewModel 로그인 실패: ${t.message!!}")
                if (t is HttpException) {
                    val errorResponse = t.response()?.errorBody()?.string()
                    Timber.e("HTTP 실패: $errorResponse")
                }
                _postLoginUserState.value = UiState.Failure("로그인에 실패했습니다\n사유: ${t.message}")
            }
        }
    }

    private fun saveAccessToken(accessToken: String, refreshToken: String) =
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepository.saveAccessToken(accessToken, refreshToken)
        }

    private fun saveUserId(userId: Int) =
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepository.saveUserId(userId)
        }
}