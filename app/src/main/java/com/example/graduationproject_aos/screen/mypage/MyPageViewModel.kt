package com.example.graduationproject_aos.screen.mypage

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.graduationproject_aos.data.model.response.ResponseGetMyInfo
import com.example.graduationproject_aos.domain.repository.UserRepository
import com.example.graduationproject_aos.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val userRepository: UserRepository,
) : ViewModel() {

    private val _getMyInfoState =
        MutableStateFlow<UiState<ResponseGetMyInfo>>(UiState.Loading)
    val getMyInfoState: StateFlow<UiState<ResponseGetMyInfo>> =
        _getMyInfoState.asStateFlow()

    fun getMyInfo() {
        viewModelScope.launch {
            userRepository.getMyInfo().onSuccess { response ->
                _getMyInfoState.value = UiState.Success(response)
                Timber.e("내 정보 호출 성공 $response")
            }.onFailure { t ->
                Log.e("ABCD", "내 정보 호출 실패: ${t.message!!}")
                if (t is HttpException) {
                    val errorResponse = t.response()?.errorBody()?.string()
                    Timber.e("HTTP 실패: $errorResponse")
                }
                _getMyInfoState.value = UiState.Failure("내 정보 호출에 실패했습니다\n사유: ${t.message}")
            }
        }
    }

    fun resetMyInfoState() {
        _getMyInfoState.value = UiState.Empty
    }
}