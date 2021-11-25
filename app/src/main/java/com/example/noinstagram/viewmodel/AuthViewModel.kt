package com.example.noinstagram.viewmodel

import androidx.lifecycle.ViewModel
import com.example.noinstagram.model.UserModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class AuthViewModel : ViewModel() {
    private val _user: MutableStateFlow<UserModel?> = MutableStateFlow(null)
    val user: StateFlow<UserModel?> = _user

    suspend fun signIn(email: String, displayName: String) {
        delay(2000)
        _user.value = UserModel(email, displayName)
    }
}