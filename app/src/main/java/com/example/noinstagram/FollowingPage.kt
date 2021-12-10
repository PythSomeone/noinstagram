package com.example.noinstagram

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.noinstagram.data.UsersRepository
import com.example.noinstagram.ui.components.FollowingToFollowersSection
import com.example.noinstagram.ui.components.ListOfFollowing


@Composable
fun FollowingScreen() {
    val userState = remember {
        UsersRepository
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Spacer(modifier = Modifier.height(4.dp))
        FollowingToFollowersSection()
        Spacer(modifier = Modifier.height(25.dp))
        ListOfFollowing(userState)
    }
}

@Composable
@Preview
fun FollowingPreview() {
    FollowingScreen()
}