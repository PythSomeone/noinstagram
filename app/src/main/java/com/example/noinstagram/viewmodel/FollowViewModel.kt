package com.example.noinstagram.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.noinstagram.data.UsersRepository
import com.example.noinstagram.model.UserModel
import com.example.noinstagram.utils.LoadingState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FollowViewModel : ViewModel() {
    private val loadingState = MutableStateFlow(LoadingState.IDLE)
    private val userRepo = UsersRepository
    private val _followers = MutableStateFlow(emptyList<UserModel>())
    val followers: StateFlow<List<UserModel>> = _followers

    private val _followersCount = MutableStateFlow("0")
    val followersCount: StateFlow<String> = _followersCount

    private val _followingCount = MutableStateFlow("0")
    val followingCount: StateFlow<String> = _followingCount

    private val _following = MutableStateFlow(emptyList<UserModel>())
    val following: StateFlow<List<UserModel>> = _following

    private val _isFollowed = MutableStateFlow(false)
    val isFollowed: StateFlow<Boolean> = _isFollowed

    private val _isFollowedText = MutableStateFlow("Follow")
    val isFollowedText: StateFlow<String> = _isFollowedText

    private val _isDeleted = MutableStateFlow(false)
    val isDeleted: StateFlow<Boolean> = _isDeleted

    private val _isDeletedText = MutableStateFlow("Delete")
    val isDeletedText: StateFlow<String> = _isDeletedText

    fun deleteUser() = viewModelScope.launch(Dispatchers.IO) {
        try {
            loadingState.emit(LoadingState.LOADING)
            if (isDeleted.value) {
                _isDeleted.value = false
                _isDeletedText.value = "Delete"
            } else {
                _isDeleted.value = true
                _isDeletedText.value = "Undo"
            }
            loadingState.emit(LoadingState.LOADED)
        } catch (e: Exception) {
            loadingState.emit(LoadingState.error(e.localizedMessage))
        }
    }

    fun checkIsFollowed(followerUid: String, followedUid: String) =
        viewModelScope.launch(Dispatchers.IO) {
            try {
                loadingState.emit(LoadingState.LOADING)
                _isFollowed.value = userRepo.userIsFollowed(followerUid, followedUid)
                if (_isFollowed.value) {
                    _isFollowedText.value = "Unfollow"
                } else {
                    _isFollowedText.value = "Follow"
                }
                loadingState.emit(LoadingState.LOADED)
            } catch (e: Exception) {
                loadingState.emit(LoadingState.error(e.localizedMessage))
            }
        }

    fun getFollowers(uid: String) = viewModelScope.launch(Dispatchers.IO) {
        _followers.value = userRepo.getFollowers(uid)
    }

    fun getFollowing(uid: String) = viewModelScope.launch(Dispatchers.IO) {
        _following.value = userRepo.getFollowing(uid)
    }

    fun getFollowersCount(uid: String) = viewModelScope.launch(Dispatchers.IO) {
        try {
            loadingState.emit(LoadingState.LOADING)
            _followersCount.value = userRepo.getFollowers(uid).distinct().count().toString()
            loadingState.emit(LoadingState.LOADED)
        } catch (e: Exception) {
            loadingState.emit(LoadingState.error(e.localizedMessage))
        }

    }

    fun getFollowingCount(uid: String) = viewModelScope.launch(Dispatchers.IO) {
        try {
            loadingState.emit(LoadingState.LOADING)
            _followingCount.value = userRepo.getFollowing(uid).distinct().count().toString()
            loadingState.emit(LoadingState.LOADED)
        } catch (e: Exception) {
            loadingState.emit(LoadingState.error(e.localizedMessage))
        }
    }

    fun followUser(currentUserUid: String, uid: String) = viewModelScope.launch(Dispatchers.IO) {
        try {
            loadingState.emit(LoadingState.LOADING)
            userRepo.toggleFollow(currentUserUid, uid)
            loadingState.emit(LoadingState.LOADED)
        } catch (e: Exception) {
            loadingState.emit(LoadingState.error(e.localizedMessage))
        }
    }


}