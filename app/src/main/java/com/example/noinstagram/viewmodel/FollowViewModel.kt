package com.example.noinstagram.viewmodel

import androidx.lifecycle.ViewModel
import com.example.noinstagram.data.UsersRepository

class FollowViewModel : ViewModel() {
    val userState = UsersRepository
    fun getFollowers(uid: String) = userState.getFollowers(uid)
    fun getFollowing(uid: String) = userState.getFollowing(uid)
    fun followUser(uid: String) = userState.followUser(uid)
}