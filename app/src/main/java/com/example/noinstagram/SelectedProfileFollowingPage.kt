package com.example.noinstagram

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.noinstagram.ui.components.FollowingToFollowersSectionSelectedProfile
import com.example.noinstagram.ui.components.ListOfFollowingSelectedProfile

@ExperimentalMaterialApi
@Composable
fun SelectedProfileFollowingScreen(navController: NavHostController, uid: String?) {
    Column(modifier = Modifier.fillMaxSize()) {
        Spacer(modifier = Modifier.height(4.dp))
        FollowingToFollowersSectionSelectedProfile(navController, uid)
        Spacer(modifier = Modifier.height(25.dp))
        ListOfFollowingSelectedProfile(navController, uid)
    }
}