package com.example.noinstagram

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.noinstagram.ui.buttons.FacebookButton
import com.example.noinstagram.ui.buttons.GoogleButton
import com.example.noinstagram.ui.buttons.LoginWithEmailButton
import com.example.noinstagram.ui.canva.NoInstagramCanvas
import com.example.noinstagram.ui.theme.NoInstagramTheme
import com.example.noinstagram.utils.AuthResultContract
import com.example.noinstagram.viewmodel.AuthViewModel
import com.facebook.login.widget.LoginButton
import com.google.android.gms.common.api.ApiException
import kotlinx.coroutines.launch

@Composable
fun FirstPage(navController: NavController, authViewModel: AuthViewModel) {
    val coroutineScope = rememberCoroutineScope()
    var text by remember { mutableStateOf<String?>(null) }
    val user by remember(authViewModel) { authViewModel.user }.collectAsState()
    val signInRequestCode = 1


    val authResultLauncher =
        rememberLauncherForActivityResult(contract = AuthResultContract()) { task ->
            try {
                val account = task?.getResult(ApiException::class.java)
                if (account == null) {
                    text = "Google sign in failed"
                } else {
                    coroutineScope.launch {
                        authViewModel.signIn(
                            email = account.email,
                            displayName = account.displayName,
                        )
                    }
                }
            } catch (e: ApiException) {
                text = "Google sign in failed"
            }
        }


    Surface(
        color = MaterialTheme.colors.primary,
        modifier = Modifier
            .fillMaxSize()
    ) {

        FirstPageBackground()

        FirstPageScreenContent(
            navController,
            onClick = {
                text = null
                authResultLauncher.launch(signInRequestCode)
            })
    }
}

@Composable
fun FirstPageScreenContent(
    navController: NavController,
    onClick: () -> Unit
) {
    val facebookLoginButton = LoginButton(LocalContext.current)
    var isLoading by remember { mutableStateOf(false) }
    var isFacebookLoading by remember { mutableStateOf(false) }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Spacer(Modifier.height(70.dp))
        NoInstagramCanvas()
        Spacer(Modifier.height(50.dp))
        FacebookButton(text = "Sign in with Facebook",
            isFacebookLoading = isFacebookLoading,
            icon = painterResource(id = R.drawable.ic_facebook_logo),
            onFacebookBtnClick = {
                isFacebookLoading = true
                facebookLoginButton.performClick()
            })
        Spacer(Modifier.height(30.dp))
        GoogleButton(navController,
            text = "Sign in with Google",
            loadingText = "Signing in...",
            isLoading = isLoading,
            icon = painterResource(id = R.drawable.ic_google_logo),
            onClick = {
                isLoading = true
                onClick()
            })
        Spacer(Modifier.height(30.dp))
        LoginWithEmailButton(navController)
        Spacer(Modifier.height(30.dp))
        SignUpTextBox(navController)
    }
}

@Composable
fun SignUpTextBox(navController: NavController) {
    TextButton(onClick = { }) {
        Text(
            text = "Sign up",
            fontSize = 16.sp,
            color = Color.White,
            fontFamily = FontFamily(Font(R.font.verdana))
        )
    }
}


@Composable
fun FirstPageBackground() {
    Image(
        painterResource(id = R.mipmap.ic_first_page_background_foreground),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier.fillMaxHeight(),
    )
}

@Preview
@Composable
private fun FirstPagePreview() {
    NoInstagramTheme {
        FirstPage(navController = rememberNavController(), authViewModel = AuthViewModel())
    }
}

