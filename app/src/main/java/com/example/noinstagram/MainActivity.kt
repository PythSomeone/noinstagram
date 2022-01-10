package com.example.noinstagram

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.noinstagram.ui.theme.NoInstagramTheme
import com.example.noinstagram.utils.database.PostHandler
import com.example.noinstagram.utils.database.UserHandler
import com.example.noinstagram.viewmodel.AuthViewModel
import com.google.firebase.auth.FirebaseAuth

private var listenersInitialized = false
private fun initializeListeners() {
    UserHandler.userListener()
    PostHandler.postListener()
    listenersInitialized = true
}

class MainActivity : ComponentActivity() {
    private val authViewModel: AuthViewModel by viewModels()

    @ExperimentalMaterialApi
    @ExperimentalFoundationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (listenersInitialized.not())
            initializeListeners()
        setContent {
            NoInstagramTheme {
                val navController = rememberNavController()
                NavHost(
                    navController,
                    if (FirebaseAuth.getInstance().currentUser != null) {
                        "HomePage"
                    } else "FirstPage"
                )
                {
                    composable("FirstPage") {
                        FirstPage(navController, authViewModel)
                    }
                    composable("LoginPage") {
                        LoginScreen(navController)
                    }
                    composable("HomePage") {
                        HomeScreen(navController)
                    }
                    composable("RegisterPage") {
                        RegisterScreen(navController)
                    }
                }
            }
        }
    }
}