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

    private val _getAllFriendState =
        MutableStateFlow<UiState<ResponseGetFriendList>>(UiState.Loading)
    val getAllFriendState: StateFlow<UiState<ResponseGetFriendList>> =
        _getAllFriendState.asStateFlow()

    fun getAllFriend() {
        viewModelScope.launch {
            userRepository.getAllFriends().onSuccess { response ->
                _getAllFriendState.value = UiState.Success(response)
                Timber.e("성공 $response")
            }.onFailure { t ->
                Log.e("ABCD", "친구 리스트 호출 실패: ${t.message!!}")
                if (t is HttpException) {
                    val errorResponse = t.response()?.errorBody()?.string()
                    Timber.e("HTTP 실패: $errorResponse")
                }
                _getAllFriendState.value = UiState.Failure("친구 리스트 호출에 실패했습니다\n사유: ${t.message}")
            }
        }
    }
}