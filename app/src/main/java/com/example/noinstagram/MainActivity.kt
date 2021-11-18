package com.example.noinstagram

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.noinstagram.ui.theme.NoInstagramTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NoInstagramTheme {
                val navController = rememberNavController()

                NavHost(navController, "FirstPage") {
                    composable("FirstPage") {
                        FirstPage(navController)
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

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    NoInstagramTheme {
        val navController = rememberNavController()

        NavHost(navController, "FirstPage") {
            composable("FirstPage") {
                FirstPage(navController)
            }
        }
    }
}