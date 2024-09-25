package com.example.graduationproject_aos.screen.friend

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.graduationproject_aos.data.model.response.ResponseGetFriendList
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
class FriendViewModel @Inject constructor(
    private val userRepository: UserRepository,
) : ViewModel() {

    private val _getFriendListState =
        MutableStateFlow<UiState<ResponseGetFriendList>>(UiState.Loading)
    val getFriendListState: StateFlow<UiState<ResponseGetFriendList>> =
        _getFriendListState.asStateFlow()

    fun resetFriendListState() {
        _getFriendListState.value = UiState.Empty
    }

    fun getAllFriend() {
        viewModelScope.launch {
            userRepository.getAllFriends().onSuccess { response ->
                _getFriendListState.value = UiState.Success(response)
                Timber.e("전체 친구 리스트 호출 성공 $response")
            }.onFailure { t ->
                Log.e("ABCD", "전체 친구 리스트 호출 실패: ${t.message!!}")
                if (t is HttpException) {
                    val errorResponse = t.response()?.errorBody()?.string()
                    Timber.e("HTTP 실패: $errorResponse")
                }
                _getFriendListState.value = UiState.Failure("친구 리스트 호출에 실패했습니다\n사유: ${t.message}")
            }
        }
    }

    fun getRequestedFriend() {
        viewModelScope.launch {
            userRepository.getFriendRequested().onSuccess { response ->
                _getFriendListState.value = UiState.Success(response)
                Timber.e("친구 요청한 친구 리스트 호출 성공 $response")
            }.onFailure { t ->
                Log.e("ABCD", "친구 요청한 친구 리스트 호출 실패: ${t.message!!}")
                if (t is HttpException) {
                    val errorResponse = t.response()?.errorBody()?.string()
                    Timber.e("HTTP 실패: $errorResponse")
                }
                _getFriendListState.value = UiState.Failure("친구 요청한 친구 리스트 호출에 실패했습니다\n사유: ${t.message}")
            }
        }
    }

    fun getRequestFriend() {
        viewModelScope.launch {
            userRepository.getFriendRequest().onSuccess { response ->
                _getFriendListState.value = UiState.Success(response)
                Timber.e("친구 요청중인 친구 리스트 호출 성공 $response")
            }.onFailure { t ->
                Log.e("ABCD", "친구 요청중인 친구 리스트 호출 실패: ${t.message!!}")
                if (t is HttpException) {
                    val errorResponse = t.response()?.errorBody()?.string()
                    Timber.e("HTTP 실패: $errorResponse")
                }
                _getFriendListState.value = UiState.Failure("친구 요청중인 친구 리스트 호출에 실패했습니다\n사유: ${t.message}")
            }
        }
    }
}