package com.example.noinstagram

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.noinstagram.data.PostsRepository
import com.example.noinstagram.data.UsersRepository
import com.example.noinstagram.model.Post
import com.example.noinstagram.ui.components.PostView
import com.example.noinstagram.utils.Navigation
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Composable
fun HomeScreen(homeNavController: NavHostController) {
    val navController = rememberNavController()
    val scaffoldState = rememberScaffoldState()
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                backgroundColor = Color.White,
                title = "No_Instagram",
                homeNavController
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
fun HomeScreenUi(scope: CoroutineScope, navController: NavHostController) {
    val posts = PostsRepository.posts.value.filter { f ->
        UsersRepository.getCurrentUser()?.following!!.any { s ->
            s == f.user?.id
        }
    }

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
            itemsIndexed(posts.asReversed().distinct()) { _, post ->
                Post(
                    post,
                    onLikeToggle = {
                        scope.launch {
                            PostsRepository.toggleLike(post.id!!)
                            refreshing = true
                        }
                    },
                    navController = navController
                )
                Spacer(modifier = Modifier.height(10.dp))
            }
        }
    }

}

@Composable
fun Post(
    post: Post,
    onLikeToggle: (Post) -> Unit,
    navController: NavHostController
) {
    PostView(post, onLikeToggle, navController)
}