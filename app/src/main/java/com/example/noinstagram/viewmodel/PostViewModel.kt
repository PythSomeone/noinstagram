package com.example.noinstagram.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.noinstagram.model.Post
import com.example.noinstagram.model.UserModel
import com.example.noinstagram.utils.LoadingState
import com.example.noinstagram.utils.database.PostHandler
import com.example.noinstagram.utils.database.UserHandler
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class PostViewModel : ViewModel() {
    val loadingState = MutableStateFlow(LoadingState.IDLE)
    private val postHandler = PostHandler

    fun uploadImage(image: String, user: UserModel) = viewModelScope.launch {
        try {
            loadingState.emit(LoadingState.LOADING)
            var post = Post(null, image, user,)
            PostHandler.setPost(post)
            loadingState.emit(LoadingState.LOADED)
        } catch (e: Exception) {
            loadingState.emit(LoadingState.error(e.localizedMessage))
        }
    }

}