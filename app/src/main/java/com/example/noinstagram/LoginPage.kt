package com.example.noinstagram

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.noinstagram.ui.buttons.ConfirmLoginBtn
import com.example.noinstagram.ui.canva.NoInstagramCanvas
import com.example.noinstagram.ui.outlinedtextfields.EmailOutlinedTextField
import com.example.noinstagram.ui.outlinedtextfields.PasswordOutlinedTextField
import com.example.noinstagram.ui.theme.NoInstagramTheme


@Composable
fun LoginPage(navController: NavController) {
    Surface(
        color = MaterialTheme.colors.primary,
        modifier = Modifier
            .fillMaxSize()
    ) {

        LoginPageBackground()

        LoginPageScreenContent(navController)
    }
}

@Composable
fun LoginPageScreenContent(navController: NavController) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Spacer(Modifier.height(70.dp))
        NoInstagramCanvas()
        Spacer(Modifier.height(50.dp))
        LoginToAccountTextBox()
        Spacer(Modifier.height(20.dp))
        EmailOutlinedTextField()
        Spacer(Modifier.height(20.dp))
        PasswordOutlinedTextField()
        Spacer(Modifier.height(20.dp))
        ConfirmLoginBtn()
        Spacer(Modifier.height(60.dp))
        Text(
            text = "Forgot your password?",
            fontSize = 16.sp,
            color = Color.White,
            fontFamily = FontFamily(Font(R.font.verdana))
        )
        Spacer(Modifier.height(10.dp))
        TextButton(onClick = { /* Do something! */ }) {
            Text(
                text = "Click here",
                fontSize = 16.sp,
                color = Color.Black,
                fontFamily = FontFamily(Font(R.font.verdana))
            )
        }
        Spacer(Modifier.height(10.dp))
        Text(
            text = "Don't have an account?",
            fontSize = 16.sp,
            color = Color.White,
            fontFamily = FontFamily(Font(R.font.verdana))
        )
        Spacer(Modifier.height(10.dp))
        TextButton(onClick = { /* Do something! */ }) {
            Text(
                text = "Sign up",
                fontSize = 16.sp,
                color = Color.Black,
                fontFamily = FontFamily(Font(R.font.verdana))
            )
        }
    }
}

@Composable
fun LoginToAccountTextBox() {
    Text(
        text = "Login to your account",
        fontSize = 20.sp,
        color = Color.White,
        fontFamily = FontFamily(Font(R.font.verdana))
    )
}

@Composable
fun LoginPageBackground() {
    Image(
        painterResource(id = R.mipmap.ic_first_page_background_foreground),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier.fillMaxHeight(),
    )
}

@Preview
@Composable
private fun LoginPagePreview() {
    NoInstagramTheme {
        LoginPage(navController = rememberNavController())
    }
}