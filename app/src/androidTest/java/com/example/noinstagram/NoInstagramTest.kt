package com.example.noinstagram

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performTextInput
import androidx.navigation.compose.rememberNavController
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.noinstagram.ui.theme.NoInstagramTheme
import com.example.noinstagram.viewmodel.AuthViewModel
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class Test {

    @get:Rule
    val composeTestRule = createComposeRule()

    @ExperimentalFoundationApi
    @ExperimentalMaterialApi
    @Test
    fun firstPageTest() {
        composeTestRule.setContent {
            NoInstagramTheme {
                FirstPage(navController = rememberNavController(), authViewModel = AuthViewModel())
            }
        }
        composeTestRule.onNodeWithText("Sign in with Facebook").assertIsDisplayed()
        composeTestRule.onNodeWithText("Sign in with Google").assertIsDisplayed()
        composeTestRule.onNodeWithText("Login with Email").assertIsDisplayed()
        composeTestRule.onNodeWithText("Sign up").assertIsDisplayed()
    }

    @ExperimentalFoundationApi
    @ExperimentalMaterialApi
    @Test
    fun loginPageTest() {
        composeTestRule.setContent {
            NoInstagramTheme {
                LoginScreen(navController = rememberNavController())
            }
        }

        composeTestRule.onNodeWithText("Login").assertIsDisplayed()
        composeTestRule.onNodeWithText("Email").performTextInput("TestInput")
        composeTestRule.onNodeWithText("Password").performTextInput("TestPass")
        composeTestRule.onNodeWithText("Login").assertIsEnabled()
    }

    @ExperimentalFoundationApi
    @ExperimentalMaterialApi
    @Test
    fun registerPageTest() {
        composeTestRule.setContent {
            NoInstagramTheme {
                RegisterScreen(navController = rememberNavController())
            }
        }

        composeTestRule.onNodeWithText("Register").assertIsDisplayed()
        composeTestRule.onNodeWithText("Name").performTextInput("Test")
        composeTestRule.onNodeWithText("Email").performTextInput("Test@Test")
        composeTestRule.onNodeWithText("Password").performTextInput("Test")
        composeTestRule.onNodeWithText("Register").assertIsEnabled()
    }

    @ExperimentalFoundationApi
    @Test
    fun addPostPageTest() {
        composeTestRule.setContent {
            NoInstagramTheme {
                AddPostScreen(navController = rememberNavController())
            }
        }

        composeTestRule.onNodeWithText("Choose photo").assertIsDisplayed()
        composeTestRule.onNodeWithText("Description").assertIsDisplayed()
        composeTestRule.onNodeWithText("Description").performTextInput("Test")
    }

    @ExperimentalFoundationApi
    @ExperimentalMaterialApi
    @Test
    fun homeScreenTest() {
        composeTestRule.setContent {
            NoInstagramTheme {
                HomeScreen(homeNavController = rememberNavController())
            }
        }
        composeTestRule.onNodeWithText("No_Instagram").assertIsDisplayed()
    }

    @ExperimentalFoundationApi
    @ExperimentalMaterialApi
    @Test
    fun exploreScreenTest() {
        composeTestRule.setContent {
            NoInstagramTheme {
                ExploreScreen(navController = rememberNavController())
            }
        }
        composeTestRule.onNodeWithText("Explore").assertIsDisplayed()
        composeTestRule.onNodeWithText("Search").performTextInput("test")
    }
}