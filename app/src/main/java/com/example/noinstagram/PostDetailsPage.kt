package com.example.noinstagram

import androidx.compose.runtime.*
import androidx.navigation.NavHostController
import com.example.noinstagram.data.PostsRepository
import kotlinx.coroutines.launch

@Composable
fun PostDetailsPage(postId: String?, navController: NavHostController) {
    val postState = PostsRepository
    val scope = rememberCoroutineScope()
    val post = postState.getPost(postId!!)
    var refreshing by remember { mutableStateOf(false) }
    LaunchedEffect(refreshing) {
        if (refreshing) {
            refreshing = false
        }
    }
    if (post != null) {
        Post(
            post,
            onLikeToggle = {
                scope.launch {
                    PostsRepository.toggleLike(post.id!!)
                }
                refreshing = true
            },
            navController
        )
    }
}