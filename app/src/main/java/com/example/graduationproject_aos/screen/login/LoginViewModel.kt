package com.example.graduationproject_aos.screen.login

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.preference.PreferenceManager
import com.example.graduationproject_aos.data.model.request.RequestUserSignInDto
import com.example.graduationproject_aos.data.model.response.ResponseUserSignInDto
import com.example.graduationproject_aos.domain.repository.UserRepository
import com.example.graduationproject_aos.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
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
    @ApplicationContext context: Context,
) : ViewModel() {

    private val _postLoginUserState =
        MutableStateFlow<UiState<ResponseUserSignInDto>>(UiState.Loading)
    val postLoginUserState: StateFlow<UiState<ResponseUserSignInDto>> =
        _postLoginUserState.asStateFlow()

    var userId: Int = -1
    val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

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
                Timber.e("성공 $response")
//                userId = response.data.id
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
}