package com.example.noinstagram

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.noinstagram.data.PostsRepository
import com.example.noinstagram.data.UsersRepository
import com.example.noinstagram.ui.components.PostSection
import com.example.noinstagram.ui.components.ProfileSection
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.delay

@ExperimentalFoundationApi
@Composable
fun UserProfileScreen(navController: NavHostController) {
    var refreshing by remember { mutableStateOf(false) }
    val userState = remember {
        UsersRepository
    }
    val postState = remember {
        PostsRepository
    }
    Column(modifier = Modifier.fillMaxSize()) {
        LaunchedEffect(refreshing) {
            if (refreshing) {
                delay(2000)
                refreshing = false
            }
        }
        Spacer(modifier = Modifier.height(4.dp))
        ProfileSection(navController, modifier = Modifier, postState, userState)
        Spacer(modifier = Modifier.height(25.dp))
        //refresh
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
                modifier = Modifier.fillMaxWidth(),
                postState,
                userState,
                navController
            )
        }
    }
}