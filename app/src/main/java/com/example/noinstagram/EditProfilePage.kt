package com.example.noinstagram

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.noinstagram.data.UsersRepository
import com.example.noinstagram.ui.components.EditProfileSection

@ExperimentalFoundationApi
@Composable
fun EditProfileScreen(
    navController: NavController
) {

    val userState = remember {
        UsersRepository
    }
    val user = userState.getCurrentUser()

    Log.d("TAG", "$user")
    Column(modifier = Modifier.fillMaxSize()) {
        Spacer(modifier = Modifier.height(4.dp))
        EditProfileSection(user!!, navController)
        Spacer(modifier = Modifier.height(25.dp))
    }
}

@ExperimentalFoundationApi
@Composable
@Preview
fun EditProfilePreview() {
    EditProfileScreen(rememberNavController())
}

