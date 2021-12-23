package com.example.noinstagram

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.example.noinstagram.data.PostsRepository
import com.example.noinstagram.model.Post
import com.example.noinstagram.model.UserModel
import com.example.noinstagram.ui.components.PostView
import com.example.noinstagram.utils.Navigation
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@ExperimentalFoundationApi
@Composable
fun HomeScreen(user: UserModel) {
    val navController = rememberNavController()
    val scaffoldState = rememberScaffoldState()
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                backgroundColor = Color.White,
                title = "No_Instagram"
            )
        },
        bottomBar = { BottomNavigationBar(navController) },
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            Navigation(navController = navController)
        }
    }
}

@Composable
fun HomeScreenUi(scope: CoroutineScope) {
    val posts by PostsRepository.posts
    var refreshing by remember { mutableStateOf(false) }
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
        LazyColumn(modifier = Modifier.padding(top = 10.dp)) {
            itemsIndexed(posts) { _, post ->
                Post(
                    post,
                    onLikeToggle = {
                        scope.launch {
                            PostsRepository.toggleLike(post.id!!)
                        }
                    }
                )
                Spacer(modifier = Modifier.height(10.dp))
            }
        }
    }

}

@Composable
fun Post(
    post: Post,
    onLikeToggle: (Post) -> Unit
) {
    PostView(post, onLikeToggle)
}