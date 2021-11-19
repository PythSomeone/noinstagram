package com.example.noinstagram

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.noinstagram.ui.theme.LightBlue
import com.example.noinstagram.ui.theme.NoInstagramTheme
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import androidx.compose.material.ExtendedFloatingActionButton as ExtendedFloatingActionButton1


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
        AppTitleName()
        Spacer(Modifier.height(100.dp))
    }
}


@Composable
fun AppTitleName() {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val pxValue = LocalDensity.current.run { 40.dp.toPx() }
    val strokePxValue = LocalDensity.current.run { 2.dp.toPx() }
    val textPaintStroke = Paint().asFrameworkPaint().apply {
        isAntiAlias = true
        style = android.graphics.Paint.Style.STROKE
        textSize = pxValue
        color = android.graphics.Color.WHITE
        strokeWidth = strokePxValue
        strokeMiter = strokePxValue - 1
        strokeJoin = android.graphics.Paint.Join.ROUND
    }

    val textPaint = Paint().asFrameworkPaint().apply {
        isAntiAlias = true
        style = android.graphics.Paint.Style.FILL
        textSize = pxValue
        color = android.graphics.Color.TRANSPARENT

    }

//    Canvas(
//        modifier = Modifier.fillMaxWidth(),
//        onDraw = {
//            drawIntoCanvas {
//                it.nativeCanvas.drawText(
//                    "No",
//                    it.nativeCanvas.width/2,
//                    0.dp.toPx(),
//                    textPaintStroke
//                )
//                it.nativeCanvas.drawText(
//                    "Instagram",
//                    100.dp.toPx(),
//                    50.dp.toPx(),
//                    textPaintStroke
//                )
//
//            }
//        }
//    )
    Text(
        text = "No",
        fontSize = 50.sp,
        color = Color.White,
        fontFamily = FontFamily(Font(R.font.verdana)),
    )
    Text(
        text = "Instagram",
        fontSize = 50.sp,
        color = Color.White,
        fontFamily = FontFamily(Font(R.font.verdana))
    )
    Spacer(Modifier.height(100.dp))

    Text(
        text = "Login to your account",
        fontSize = 20.sp,
        color = Color.White,
        fontFamily = FontFamily(Font(R.font.verdana))
    )

    Spacer(Modifier.height(20.dp))

    OutlinedTextField(
        value = email,
//        leadingIcon = { Icon(imageVector = Icons.Default.Email, contentDescription = null) },
        colors = TextFieldDefaults.outlinedTextFieldColors(focusedBorderColor = Color.White, unfocusedBorderColor = Color.White),
        shape = RoundedCornerShape(24.dp),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        label = { Text(text = "Email", color = Color.White) },
//        placeholder = { Text(text = "Your email") },
        onValueChange = {
            email = it
        },
        modifier = Modifier.width(300.dp)
    )

    Spacer(Modifier.height(20.dp))

    OutlinedTextField(
        value = password,
        colors = TextFieldDefaults.outlinedTextFieldColors(focusedBorderColor = Color.White, unfocusedBorderColor = Color.White),
        shape = RoundedCornerShape(24.dp),
        label = { Text(text = "Password", color = Color.White) },
//        placeholder = { Text(text = "12334444") },
        visualTransformation = PasswordVisualTransformation(),
        onValueChange = {
            password = it
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        modifier = Modifier.width(300.dp)

    )

    Spacer(Modifier.height(20.dp))

    ExtendedFloatingActionButton1(
        onClick = { /* ... */ },
        shape = RoundedCornerShape(24.dp),
        modifier = Modifier
            .width(300.dp)
            .alpha(0.4f),
        text = { Text("Login") },
        backgroundColor = Color.White,

    )

    Spacer(Modifier.height(60.dp))

    Text(
        text = "Forgot your password?",
        fontSize = 16.sp,
        color = Color.White,
        fontFamily = FontFamily(Font(R.font.verdana))
    )
    Spacer(Modifier.height(10.dp))
    TextButton(onClick = { /* Do something! */ }) {
        Text(text = "Click here",
            fontSize = 16.sp,
            color = Color.Black,
            fontFamily = FontFamily(Font(R.font.verdana)))
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

//    OutlinedTextField(
//        value = text,
//        modifier = Modifier
//            .padding(8.dp)
//            .fillMaxWidth(),
//        label = { Text(text = "Password") },
//        placeholder = { Text(text = "12334444") },
//        visualTransformation = PasswordVisualTransformation(),
//        onValueChange = {
//            text = it
//        },
//        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
//    )
//
//    // Outlined Input text with icon on the left
//    // inside leadingIcon property add the icon
//    OutlinedTextField(
//        value = text,
//        leadingIcon = { Icon(imageVector = Icons.Default.Email, contentDescription = null) },
//        modifier = Modifier
//            .padding(8.dp)
//            .fillMaxWidth(),
//        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
//        label = { Text(text = "Email address") },
//        placeholder = { Text(text = "Your email") },
//        onValueChange = {
//            text = it
//        }
//    )
    }

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