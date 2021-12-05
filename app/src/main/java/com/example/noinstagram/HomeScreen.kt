package com.example.noinstagram

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.example.noinstagram.data.PostsRepository
import com.example.noinstagram.model.Post
import com.example.noinstagram.utils.Navigation
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun HomeScreen() {
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
    ) {
        Navigation(navController = navController)
    }
}

@Composable
fun HomeScreenUi(scope: CoroutineScope) {
    val posts by PostsRepository.posts
    LazyColumn(modifier = Modifier.padding(top = 10.dp)) {
        itemsIndexed(posts) { _, post ->
            Post(post,
                onLikeToggle = {
                    scope.launch {
                        PostsRepository.toggleLike(post.id)
                    }
                }
            )
            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}

@Composable
private fun Post(
    post: Post,
    onLikeToggle: (Post) -> Unit
) {
    PostView(post, onLikeToggle)
}