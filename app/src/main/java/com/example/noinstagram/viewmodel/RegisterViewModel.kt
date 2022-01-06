package com.example.noinstagram.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.noinstagram.model.UserModel
import com.example.noinstagram.utils.LoadingState
import com.example.noinstagram.utils.database.UserHandler
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class RegisterViewModel : ViewModel() {
    val loadingState = MutableStateFlow(LoadingState.IDLE)
    private val accountHandler = UserHandler

    fun createUserWithEmailAndPassword(
        username: String,
        email: String,
        password: String,
        navController: NavController
    ) = viewModelScope.launch {
        try {
            loadingState.emit(LoadingState.LOADING)
            Firebase.auth.createUserWithEmailAndPassword(email, password).addOnSuccessListener {
                val uid = it.user?.uid!!
                val user = UserModel(
                    id = uid,
                    email = email,
                    displayName = username
                )
                accountHandler.setUser(user)
                navController.navigate("HomePage")
            }

            loadingState.emit(LoadingState.LOADED)
        } catch (e: Exception) {
            loadingState.emit(LoadingState.error(e.localizedMessage))
        }
    }

}