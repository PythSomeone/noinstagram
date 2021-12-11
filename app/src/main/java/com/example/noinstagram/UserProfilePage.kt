package com.example.noinstagram

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.noinstagram.data.UsersRepository
import com.example.noinstagram.model.UserModel
import com.example.noinstagram.ui.components.PostSection
import com.example.noinstagram.ui.components.ProfileSection
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.google.firebase.database.ktx.getValue
import kotlinx.coroutines.delay

@ExperimentalFoundationApi
@Composable
fun UserProfileScreen() {
    val userState = remember {
        UsersRepository
    }
    var user = UserModel()
    var refreshing by remember { mutableStateOf(false) }

    userState.userSnapshots.value.forEach(action = {
        if (it.key == "123")
            user = it.getValue<UserModel>()!!
    })
    Log.d("TAG", "$user")
    Column(modifier = Modifier.fillMaxSize()) {
        Spacer(modifier = Modifier.height(4.dp))
        ProfileSection(user)
        Spacer(modifier = Modifier.height(25.dp))
        //refresh
        LaunchedEffect(refreshing) {
            if (refreshing) {
                delay(2000)
                refreshing = false
            }
        }
        SwipeRefresh(
            state = rememberSwipeRefreshState(refreshing),
            onRefresh = { refreshing = true },
            indicator = { state, refreshTriggerDistance ->
                SwipeRefreshIndicator(
                    state = state,
                    refreshTriggerDistance = refreshTriggerDistance,
                    scale = true
                )
            }
        ) {
            PostSection(
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@ExperimentalFoundationApi
@Composable
@Preview
fun UserProfilePreview() {
    UserProfileScreen()
}