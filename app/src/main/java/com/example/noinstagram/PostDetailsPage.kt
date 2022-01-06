package com.example.noinstagram

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.*
import androidx.navigation.NavHostController
import com.example.noinstagram.data.PostsRepository
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.launch

@ExperimentalMaterialApi
@Composable
fun PostDetailsPage(postId: String?, navController: NavHostController) {
    val postState = PostsRepository
    val scope = rememberCoroutineScope()
    val post = postState.getPost(postId!!)
    val refreshing = remember { mutableStateOf(false) }
    LaunchedEffect(refreshing) {
        if (refreshing.value) {
            refreshing.value = false
        }
    }
    if (post != null) {
        SwipeRefresh(
            state = rememberSwipeRefreshState(refreshing.value),
            onRefresh = { refreshing.value = true },
            indicator = { state, refreshTriggerDistance ->
                SwipeRefreshIndicator(
                    state = state,
                    refreshTriggerDistance = refreshTriggerDistance,
                    scale = true
                )
            }
        ) {
            Post(
                post,
                onLikeToggle = {
                    scope.launch {
                        PostsRepository.toggleLike(post.id!!)
                        refreshing.value = true
                        refreshing.value = false
                    }

                },
                navController,
                refreshing
            )
        }
    }
}