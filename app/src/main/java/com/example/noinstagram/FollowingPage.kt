package com.example.noinstagram

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.noinstagram.data.UsersRepository
import com.example.noinstagram.ui.components.FollowingToFollowersSection
import com.example.noinstagram.ui.components.ListOfFollowing


@ExperimentalMaterialApi
@Composable
fun FollowingScreen(navController: NavHostController) {
    val userState = remember {
        UsersRepository
    }
    val currentUserUid = userState.getCurrentUser()?.id
    Column(modifier = Modifier.fillMaxSize()) {
        Spacer(modifier = Modifier.height(4.dp))
        FollowingToFollowersSection(navController)
        Spacer(modifier = Modifier.height(25.dp))
        ListOfFollowing(userState, currentUserUid, navController)
    }
}