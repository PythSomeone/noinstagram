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
import com.example.noinstagram.ui.components.FollowersToFollowingSectionSelectedProfile
import com.example.noinstagram.ui.components.ListOfSelectedProfileFollowers


@ExperimentalMaterialApi
@Composable
fun SelectedProfileFollowersPage(navController: NavHostController, uid: String?) {
    Column(modifier = Modifier.fillMaxSize()) {
        Spacer(modifier = Modifier.height(4.dp))
        FollowersToFollowingSectionSelectedProfile(navController, uid)
        Spacer(modifier = Modifier.height(25.dp))
        ListOfSelectedProfileFollowers(uid, navController)
    }
}