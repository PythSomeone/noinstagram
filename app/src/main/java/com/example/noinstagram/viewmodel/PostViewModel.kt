package com.example.noinstagram.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.noinstagram.utils.LoadingState
import com.example.noinstagram.utils.database.PostHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class PostViewModel : ViewModel() {
    private val loadingState = MutableStateFlow(LoadingState.IDLE)
    private val postHandler = PostHandler

    fun uploadImage(uri: Uri, description: String?) = viewModelScope.launch {
        try {
            loadingState.emit(LoadingState.LOADING)


            postHandler.createNewPost(uri, description)


            loadingState.emit(LoadingState.LOADED)
        } catch (e: Exception) {
            loadingState.emit(LoadingState.error(e.localizedMessage))
        }
    }



}