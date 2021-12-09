package com.example.noinstagram.utils

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.noinstagram.HomeScreenUi
import com.example.noinstagram.R
import com.example.noinstagram.UserProfileScreen
import com.example.noinstagram.data.UsersRepository


sealed class NavigationItem(var route: String, var icon: Int, var title: String) {
    object Home : NavigationItem("home", R.drawable.ic_baseline_home_24, "Home")
    object Search : NavigationItem("search", R.drawable.ic_baseline_search_24, "Search")
    object AddPost : NavigationItem("addPost", R.drawable.ic_outline_photo_camera_24, "Add Post")
    object Followers : NavigationItem("followers", R.drawable.ic_baseline_people_24, "Followers")
    object Profile : NavigationItem("profile", R.drawable.ic_baseline_person_24, "Profile")
}

@ExperimentalFoundationApi
@Composable
fun Navigation(navController: NavHostController) {
    NavHost(navController, startDestination = NavigationItem.Home.route) {
        composable(NavigationItem.Home.route) {
            HomeScreenUi(scope = rememberCoroutineScope())
        }
        composable(NavigationItem.Search.route) {
            //SearchUi()
        }
        composable(NavigationItem.AddPost.route) {
            //AddPostUi()
        }
        composable(NavigationItem.Followers.route) {
            //FollowersUi()
        }
        composable(NavigationItem.Profile.route) {
            UserProfileScreen()
        }
    }
}