package com.example.graduationproject_aos.screen.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.graduationproject_aos.data.model.request.RequestUserSignInDto
import com.example.graduationproject_aos.data.model.response.ResponseRoomDto
import com.example.graduationproject_aos.data.model.response.ResponseUserSignInDto
import com.example.graduationproject_aos.domain.repository.DataStoreRepository
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
class HomeViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val dataStoreRepository: DataStoreRepository
) : ViewModel() {
    private val _createRoomState =
        MutableStateFlow<UiState<ResponseRoomDto>>(UiState.Loading)
    val createRoomState: StateFlow<UiState<ResponseRoomDto>> =
        _createRoomState.asStateFlow()

    fun createRoom() {
        viewModelScope.launch {
            userRepository.createRoom().onSuccess { response ->
                _createRoomState.value = UiState.Success(response)
                Timber.e("성공 $response")
            }.onFailure { t ->
                val errorMessage = when (t) {
                    is HttpException -> {
                        val errorResponse = t.response()?.errorBody()?.string() ?: "서버에서 응답을 받을 수 없습니다."
                        Timber.e("HTTP 실패: $errorResponse")
                        "화상통화 방 생성 실패했습니다\n사유: $errorResponse"
                    }
                    else -> {
                        Timber.e("화상통화 방 생성 실패: ${t.message!!}")
                        "화상통화 방 생성 실패했습니다\n사유: ${t.message}"
                    }
                }
                _createRoomState.value = UiState.Failure(errorMessage)
            }
        }
    }

    fun resetCreateRoomState() {
        _createRoomState.value = UiState.Empty
    }
}