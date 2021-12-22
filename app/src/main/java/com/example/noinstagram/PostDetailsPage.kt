package com.example.noinstagram

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import com.example.noinstagram.data.PostsRepository
import kotlinx.coroutines.launch

@Composable
fun PostDetailsPage(postId: String?) {
    val postState = remember {
        PostsRepository
    }
    val scope = rememberCoroutineScope()
    val post = postState.getPost(postId!!)
    if (post != null) {
        Post(
            post,
            onLikeToggle = {
                scope.launch {
                    PostsRepository.toggleLike(post.id!!)
                }
            }
        )
    }
}