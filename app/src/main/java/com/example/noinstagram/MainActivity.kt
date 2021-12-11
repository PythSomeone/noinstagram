package com.example.noinstagram

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.noinstagram.model.UserModel
import com.example.noinstagram.ui.theme.NoInstagramTheme
import com.example.noinstagram.utils.database.UserHandler
import com.example.noinstagram.viewmodel.AuthViewModel
import com.google.firebase.auth.FirebaseAuth


class MainActivity : ComponentActivity() {
    private val authViewModel: AuthViewModel by viewModels()

    @ExperimentalFoundationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        UserHandler.userListener()
        setContent {
            NoInstagramTheme {
                val navController = rememberNavController()
                val userModel = FirebaseAuth.getInstance().currentUser
                val user = UserModel(userModel?.email, userModel?.displayName)
                NavHost(
                    navController,
                    if (FirebaseAuth.getInstance().currentUser != null) "HomePage" else "FirstPage"
                )
                {
                    composable("FirstPage") {
                        FirstPage(navController, authViewModel)
                    }
                    composable("LoginPage") {
                        LoginScreen(navController)
                    }
                    composable("HomePage") {
                        HomeScreen(user)
                    }
                }
            }
        }
    }
}