package com.example.noinstagram

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.noinstagram.data.PostsRepository
import com.example.noinstagram.data.UsersRepository
import com.example.noinstagram.ui.components.SelectedProfilePostSection
import com.example.noinstagram.ui.components.SelectedProfileSection
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.delay

@ExperimentalFoundationApi
@Composable
fun PublicProfileScreen(id: String?, navController: NavHostController) {
    var refreshing by remember { mutableStateOf(false) }
    val userState = remember {
        UsersRepository
    }
    val user = id?.let { userState.getUser(it) }
    val postState = remember {
        PostsRepository
    }
    Column(modifier = Modifier.fillMaxSize()) {
        Spacer(modifier = Modifier.height(4.dp))
        SelectedProfileSection(
            modifier = Modifier,
            postState = postState,
            user = user,
            navController
        )
        Spacer(modifier = Modifier.height(25.dp))

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
            SelectedProfilePostSection(
                modifier = Modifier.fillMaxWidth(),
                postState,
                navController,
                user
            )
        }
    }
}


