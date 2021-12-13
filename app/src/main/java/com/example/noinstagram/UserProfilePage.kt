package com.example.noinstagram

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.noinstagram.data.UsersRepository
import com.example.noinstagram.model.UserModel
import com.example.noinstagram.ui.components.PostSection
import com.example.noinstagram.ui.components.ProfileSection
import com.google.firebase.database.ktx.getValue

@ExperimentalFoundationApi
@Composable
fun UserProfileScreen() {
    val userState = remember {
        UsersRepository
    }
    var user = UserModel()
    userState.users.value.forEach(action = {
        if (it.id == "123")
            user = it
    })
    Log.d("TAG", "$user")
    Column(modifier = Modifier.fillMaxSize()) {
        Spacer(modifier = Modifier.height(4.dp))
        ProfileSection(user)
        Spacer(modifier = Modifier.height(25.dp))
        PostSection(
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@ExperimentalFoundationApi
@Composable
@Preview
fun UserProfilePreview() {
    UserProfileScreen()
}