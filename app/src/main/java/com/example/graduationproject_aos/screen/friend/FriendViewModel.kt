package com.example.graduationproject_aos.screen.friend

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.graduationproject_aos.data.model.response.ResponseDto
import com.example.graduationproject_aos.data.model.response.ResponseFriendRequestedApproveDto
import com.example.graduationproject_aos.data.model.response.ResponseGetFriendList
import com.example.graduationproject_aos.data.model.response.ResponsePatchFriendRequestRemove
import com.example.graduationproject_aos.data.model.response.ResponsePostFriendRequest
import com.example.graduationproject_aos.data.model.response.ResponseSearchFriendList
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

    private val _getSearchFriendListState =
        MutableStateFlow<UiState<ResponseSearchFriendList>>(UiState.Loading)
    val getSearchFriendListState: StateFlow<UiState<ResponseSearchFriendList>> =
        _getSearchFriendListState.asStateFlow()

    // 친구 삭제
    private val _deleteFriendState =
        MutableStateFlow<UiState<ResponseDto>>(UiState.Loading)
    val deleteFriendState: StateFlow<UiState<ResponseDto>> =
        _deleteFriendState.asStateFlow()

    // 친구 요청 수락
    private val _postRequestedApproveState =
        MutableStateFlow<UiState<ResponseFriendRequestedApproveDto>>(UiState.Loading)
    val postRequestedApproveState: StateFlow<UiState<ResponseFriendRequestedApproveDto>> =
        _postRequestedApproveState.asStateFlow()

    // 친구 요청
    private val _postRequestState =
        MutableStateFlow<UiState<ResponsePostFriendRequest>>(UiState.Loading)
    val postRequestState: StateFlow<UiState<ResponsePostFriendRequest>> =
        _postRequestState.asStateFlow()

    // 친구 요청 거절
    private val _patchRequestedRemoveState =
        MutableStateFlow<UiState<ResponsePatchFriendRequestRemove>>(UiState.Loading)
    val patchRequestedRemoveState: StateFlow<UiState<ResponsePatchFriendRequestRemove>> =
        _patchRequestedRemoveState.asStateFlow()

    // 친구 요청 취소
    private val _patchRequestRemoveState =
        MutableStateFlow<UiState<ResponsePatchFriendRequestRemove>>(UiState.Loading)
    val patchRequestRemoveState: StateFlow<UiState<ResponsePatchFriendRequestRemove>> =
        _patchRequestRemoveState.asStateFlow()

    fun resetFriendListState() {
        _getFriendListState.value = UiState.Empty
    }

    fun resetSearchFriendListState() {
        _getSearchFriendListState.value = UiState.Empty
    }

    fun getSearchFriend(userInfo: String) {
        viewModelScope.launch {
            userRepository.searchFriend(userInfo).onSuccess { response ->
                _getSearchFriendListState.value = UiState.Success(response)
                Timber.e("검색 친구 리스트 호출 성공 $response")
            }.onFailure { t ->
                Log.e("ABCD", "검색 친구 리스트 호출 실패: ${t.message!!}")
                if (t is HttpException) {
                    val errorResponse = t.response()?.errorBody()?.string()
                    Timber.e("HTTP 실패: $errorResponse")
                }
                _getSearchFriendListState.value = UiState.Failure("검색 친구 리스트 호출에 실패했습니다\n사유: ${t.message}")
            }
        }
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

    fun postFriendRequestedApprove(id: Int) {
        viewModelScope.launch {
            userRepository.postFriendRequestedApprove(id).onSuccess { response ->
                _postRequestedApproveState.value = UiState.Success(response)
                Timber.e("친구 요청 수락 성공 $response")
            }.onFailure { t ->
                Log.e("ABCD", "친구 요청 수락 실패: ${t.message!!}")
                if (t is HttpException) {
                    val errorResponse = t.response()?.errorBody()?.string()
                    Timber.e("HTTP 실패: $errorResponse")
                }
                _postRequestedApproveState.value = UiState.Failure("친구 요청 수락에 실패했습니다\n사유: ${t.message}")
            }
        }
    }

    fun postFriendRequest(id: Int) {
        viewModelScope.launch {
            userRepository.postFriendRequest(id).onSuccess { response ->
                _postRequestState.value = UiState.Success(response)
                Timber.e("친구 요청 성공 $response")
            }.onFailure { t ->
                Log.e("ABCD", "친구 요청 실패: ${t.message!!}")
                if (t is HttpException) {
                    val errorResponse = t.response()?.errorBody()?.string()
                    Timber.e("HTTP 실패: $errorResponse")
                }
                _postRequestState.value = UiState.Failure("친구 요청에 실패했습니다\n사유: ${t.message}")
            }
        }
    }

    fun patchFriendRequestedRemove(id: Int) {
        viewModelScope.launch {
            userRepository.patchFriendRequestedRemove(id).onSuccess { response ->
                _patchRequestedRemoveState.value = UiState.Success(response)
                Timber.e("친구 요청 거절 성공 $response")
            }.onFailure { t ->
                Log.e("ABCD", "친구 요청 거절 실패: ${t.message!!}")
                if (t is HttpException) {
                    val errorResponse = t.response()?.errorBody()?.string()
                    Timber.e("HTTP 실패: $errorResponse")
                }
                _patchRequestedRemoveState.value = UiState.Failure("친구 요청 거절에 실패했습니다\n사유: ${t.message}")
            }
        }
    }

    fun patchFriendRequestRemove(id: Int) {
        viewModelScope.launch {
            userRepository.patchFriendRequestRemove(id).onSuccess { response ->
                _patchRequestRemoveState.value = UiState.Success(response)
                Timber.e("친구 요청 취소 성공 $response")
            }.onFailure { t ->
                Log.e("ABCD", "친구 요청 취소 실패: ${t.message!!}")
                if (t is HttpException) {
                    val errorResponse = t.response()?.errorBody()?.string()
                    Timber.e("HTTP 실패: $errorResponse")
                }
                _patchRequestRemoveState.value = UiState.Failure("친구 요청 취소에 실패했습니다\n사유: ${t.message}")
            }
        }
    }

    fun deleteFriend(id: Int) {
        viewModelScope.launch {
            userRepository.deleteFriend(id).onSuccess { response ->
                _deleteFriendState.value = UiState.Success(response)
                Timber.e("친구 삭제 성공 $response")
            }.onFailure { t ->
                Log.e("ABCD", "친구 삭제 실패: ${t.message!!}")
                if (t is HttpException) {
                    val errorResponse = t.response()?.errorBody()?.string()
                    Timber.e("HTTP 실패: $errorResponse")
                }
                _deleteFriendState.value = UiState.Failure("친구 삭제에 실패했습니다\n사유: ${t.message}")
            }
        }
    }
}