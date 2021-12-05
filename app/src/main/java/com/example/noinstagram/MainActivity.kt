package com.example.noinstagram

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.noinstagram.ui.theme.NoInstagramTheme
import com.example.noinstagram.viewmodel.AuthViewModel


class MainActivity : ComponentActivity() {
    private val authViewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}