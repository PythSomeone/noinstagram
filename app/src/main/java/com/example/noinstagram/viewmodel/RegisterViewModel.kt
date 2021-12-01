package com.example.noinstagram.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.noinstagram.model.UserModel
import com.example.noinstagram.utils.LoadingState
import com.example.noinstagram.utils.database.AccountHandler
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class RegisterViewModel : ViewModel() {
    val loadingState = MutableStateFlow(LoadingState.IDLE)
    private val accountHandler = AccountHandler()

    fun createUserWithEmailAndPassword(username: String, email: String, password: String) = viewModelScope.launch {
        try {
            loadingState.emit(LoadingState.LOADING)
            Firebase.auth.createUserWithEmailAndPassword(email, password).await()
            val uid = Firebase.auth.currentUser!!.uid
            val user = UserModel(email, username)
            accountHandler.Add(user, uid)
            loadingState.emit(LoadingState.LOADED)
        } catch (e: Exception) {
            loadingState.emit(LoadingState.error(e.localizedMessage))
        }
    }

}