package com.example.noinstagram

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.noinstagram.ui.theme.LightBlue
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
            errorText = text,
            onClick = {
                text = null
                authResultLauncher.launch(signInRequestCode)
            })
    }
}

@Composable
fun FirstPageScreenContent(
    navController: NavController,
    errorText: String?,
    onClick: () -> Unit
) {
    var isLoading by remember { mutableStateOf(false) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Spacer(Modifier.height(70.dp))
        AppTitleName()
        Spacer(Modifier.height(100.dp))
        FacebookButton()
        Spacer(Modifier.height(30.dp))
        GoogleButton(text = "Sign in with Google",
            loadingText = "Signing in...",
            isLoading = isLoading,
            icon = painterResource(id = R.drawable.ic_google_logo),
            onClick = {
                isLoading = true
                onClick()
            })
    }
}

@Composable
fun GoogleButton(
    text: String,
    loadingText: String = "Signing in...",
    icon: Painter,
    isLoading: Boolean = false,
    borderColor: Color = Color.LightGray,
    backgroundColor: Color = MaterialTheme.colors.surface,
    progressIndicatorColor: Color = MaterialTheme.colors.primary,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier.clickable(
            enabled = !isLoading,
            onClick = onClick
        ),
        shape = RoundedCornerShape(22.dp),
        border = BorderStroke(width = 1.dp, color = borderColor),
        color = backgroundColor,
    ) {
        Row(
            modifier = Modifier
                .padding(
                    start = 12.dp,
                    end = 16.dp,
                    top = 12.dp,
                    bottom = 12.dp
                )
                .animateContentSize(
                    animationSpec = tween(
                        durationMillis = 300,
                        easing = LinearOutSlowInEasing
                    )
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            Icon(
                painter = icon,
                contentDescription = "SignInButton",
                tint = Color.Unspecified
            )
            Spacer(modifier = Modifier.width(8.dp))

            Text(text = if (isLoading) loadingText else text)
            if (isLoading) {
                Spacer(modifier = Modifier.width(16.dp))
                CircularProgressIndicator(
                    modifier = Modifier
                        .height(16.dp)
                        .width(16.dp),
                    strokeWidth = 2.dp,
                    color = progressIndicatorColor
                )
            }
        }
    }
}

@Composable
fun FacebookButton() {
    AndroidView(
        factory = { context ->
            LoginButton(context).apply {

            }
        },
    )
}

@Composable
fun AppTitleName() {
    val pxValue = LocalDensity.current.run { 50.dp.toPx() }
    val strokePxValue = LocalDensity.current.run { 2.dp.toPx() }
    val textPaintStroke = Paint().asFrameworkPaint().apply {
        isAntiAlias = true
        style = android.graphics.Paint.Style.STROKE
        textSize = pxValue
        color = android.graphics.Color.WHITE
        strokeWidth = strokePxValue
        strokeMiter = strokePxValue - 1
        strokeJoin = android.graphics.Paint.Join.ROUND
        textAlign = android.graphics.Paint.Align.CENTER
    }

    val textPaint = Paint().asFrameworkPaint().apply {
        isAntiAlias = true
        style = android.graphics.Paint.Style.FILL
        textSize = pxValue
        color = android.graphics.Color.TRANSPARENT
        textAlign = android.graphics.Paint.Align.CENTER
    }


    Canvas(
        modifier = Modifier.fillMaxWidth(),
        onDraw = {
            drawIntoCanvas {
                it.nativeCanvas.drawText(
                    "No",
                    center.x,
                    0.dp.toPx(),
                    textPaintStroke
                )
                it.nativeCanvas.drawText(
                    "No",
                    center.x,
                    0.dp.toPx(),
                    textPaint
                )
                it.nativeCanvas.drawText(
                    "Instagram",
                    center.x,
                    50.dp.toPx(),
                    textPaintStroke
                )
                it.nativeCanvas.drawText(
                    "Instagram",
                    center.x,
                    50.dp.toPx(),
                    textPaint
                )
            }
        }
    )
    Spacer(Modifier.height(60.dp))

    Text(
        text = "express yourself",
        fontSize = 20.sp,
        fontStyle = FontStyle.Italic,
        color = LightBlue,
        fontFamily = FontFamily(Font(R.font.verdana))
    )

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

