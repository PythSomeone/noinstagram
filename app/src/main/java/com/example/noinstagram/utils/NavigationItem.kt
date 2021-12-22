package com.example.noinstagram.utils

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.noinstagram.*
import com.example.noinstagram.model.UserModel


sealed class NavigationItem(var route: String, var icon: Int, var title: String) {
    object Home : NavigationItem("home", R.drawable.ic_baseline_home_24, "Home")
    object Search : NavigationItem("search", R.drawable.ic_baseline_search_24, "Search")
    object AddPost : NavigationItem("addPost", R.drawable.ic_outline_photo_camera_24, "Add Post")
    object Followers : NavigationItem("followers", R.drawable.ic_baseline_people_24, "Followers")
    object Profile : NavigationItem("profile", R.drawable.ic_baseline_person_24, "Profile")
}

@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Composable
fun Navigation(navController: NavHostController, user: UserModel) {
    NavHost(navController, startDestination = NavigationItem.Home.route) {
        composable(NavigationItem.Home.route) {
            HomeScreenUi(scope = rememberCoroutineScope())
        }
        composable(NavigationItem.Search.route) {
            ExploreScreen(navController)
        }
        composable(NavigationItem.AddPost.route) {
            //AddPostUi()
        }
        composable(NavigationItem.Followers.route) {
            FollowingScreen(navController)
        }
        composable(NavigationItem.Profile.route) {
            UserProfileScreen(user, navController)
        }
        composable("Post/{PostId}") { backstackEntry ->
            PostDetailsPage(
                backstackEntry.arguments?.getString("PostId")
            )
        }
        composable("FollowersPage") {
            FollowersPage(navController)
        }
    }
}