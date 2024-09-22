package com.example.graduationproject_aos.screen.signUp

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.preference.PreferenceManager
import com.example.graduationproject_aos.data.model.request.RequestUserSignInDto
import com.example.graduationproject_aos.data.model.request.RequestUserSignUpDto
import com.example.graduationproject_aos.data.model.response.ResponseDto
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
class SignUpViewModel @Inject constructor(
    private val userRepository: UserRepository,
    @ApplicationContext context: Context,
) : ViewModel() {

    private val _postSignUpUserState =
        MutableStateFlow<UiState<ResponseDto>>(UiState.Loading)
    val postSignUpUserState: StateFlow<UiState<ResponseDto>> =
        _postSignUpUserState.asStateFlow()

    fun resetSignUpState() {
        _postSignUpUserState.value = UiState.Empty
    }
    fun postSignUpUser(email: String, pw: String, nickname: String, userType: String) {
        viewModelScope.launch {
            userRepository.postSignUpUser(
                RequestUserSignUpDto(
                    email = email,
                    password = pw,
                    nickname = nickname,
                    userType = userType,
                )
            ).onSuccess { response ->
                _postSignUpUserState.value = UiState.Success(response)
                Timber.e("성공 $response")
            }.onFailure { t ->
                Log.e("ABCD", "ViewModel 회원가입 실패: ${t.message!!}")
                if (t is HttpException) {
                    val errorResponse = t.response()?.errorBody()?.string()
                    Timber.e("HTTP 실패: $errorResponse")
                }
                _postSignUpUserState.value = UiState.Failure("회원가입에 실패했습니다\n사유: ${t.message}")
            }
        }
    }
}