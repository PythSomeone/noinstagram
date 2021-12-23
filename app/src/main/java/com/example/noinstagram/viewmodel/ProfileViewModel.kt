package com.example.noinstagram.viewmodel

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.noinstagram.model.UserModel
import com.example.noinstagram.utils.LoadingState
import com.example.noinstagram.utils.database.StorageHandler
import com.example.noinstagram.utils.database.UserHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class ProfileViewModel : ViewModel() {
    val loadingState = MutableStateFlow(LoadingState.IDLE)
    private val userHandler = UserHandler
    private val storageHandler = StorageHandler

    fun changeData(user: UserModel, description: String?, displayName: String?, imageUri : Uri) = viewModelScope.launch {
        try {
            loadingState.emit(LoadingState.LOADING)
            user.description = description
            user.displayName = displayName
            Log.d("db", "working")
            val photoUrl = storageHandler.uploadPicture(imageUri, StorageHandler.Folder.PROFILE)
            user.image = photoUrl
            userHandler.setUser(user)
            loadingState.emit(LoadingState.LOADED)
        } catch (e: Exception) {
            loadingState.emit(LoadingState.error(e.localizedMessage))
        }
    }


    fun changeData(user: UserModel, description: String?, displayName: String?) = viewModelScope.launch {
        try {
            loadingState.emit(LoadingState.LOADING)
            user.description = description
            user.displayName = displayName
            userHandler.setUser(user)
            loadingState.emit(LoadingState.LOADED)
        } catch (e: Exception) {
            loadingState.emit(LoadingState.error(e.localizedMessage))
        }
    }
}