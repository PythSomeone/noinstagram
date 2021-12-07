package com.example.noinstagram

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.noinstagram.model.UserModel
import com.example.noinstagram.ui.components.PostSection
import com.example.noinstagram.ui.components.ProfileSection

@ExperimentalFoundationApi
@Composable
fun UserProfileScreen() {
    Column(modifier = Modifier.fillMaxSize()) {
        Spacer(modifier = Modifier.height(4.dp))
        ProfileSection(userModel = UserModel(email = "email@email.com", displayName = "Name"))
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