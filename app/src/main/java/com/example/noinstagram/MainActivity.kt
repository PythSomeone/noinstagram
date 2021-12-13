package com.example.noinstagram

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.noinstagram.ui.theme.NoInstagramTheme
import com.example.noinstagram.utils.database.PostHandler
import com.example.noinstagram.utils.database.UserHandler
import com.example.noinstagram.viewmodel.AuthViewModel


class MainActivity : ComponentActivity() {
    private val authViewModel: AuthViewModel by viewModels()

    @ExperimentalFoundationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        UserHandler.userListener()
        PostHandler.postListener()
        setContent {
            NoInstagramTheme {
                val navController = rememberNavController()

                NavHost(navController, "FirstPage") {
                    composable("FirstPage") {
                        FirstPage(navController, authViewModel)
                    }
                    composable("LoginPage") {
                        LoginScreen(navController)
                    }
                    composable("HomePage") {
                        HomeScreen()
                    }
                }
            }
        }
    }
}