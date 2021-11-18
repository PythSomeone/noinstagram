package com.example.noinstagram

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
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
import com.facebook.login.widget.LoginButton

@Composable
fun FirstPage(navController: NavController) {
    Surface(
        color = MaterialTheme.colors.primary,
        modifier = Modifier
            .fillMaxSize()
    ) {

        FirstPageBackground()

        FirstPageScreenContent(navController)
    }
}

@Composable
fun FirstPageScreenContent(navController: NavController) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Spacer(Modifier.height(70.dp))
        AppTitleName()
        Spacer(Modifier.height(100.dp))
        FacebookButton()
    }
}

@Composable
fun FacebookButton() {
    AndroidView(factory = { context ->
        LoginButton(context)
    })
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
    }

    val textPaint = Paint().asFrameworkPaint().apply {
        isAntiAlias = true
        style = android.graphics.Paint.Style.FILL
        textSize = pxValue
        color = android.graphics.Color.TRANSPARENT

    }

    Canvas(
        modifier = Modifier.fillMaxWidth(),
        onDraw = {
            drawIntoCanvas {
                it.nativeCanvas.drawText(
                    "No",
                    130.dp.toPx(),
                    0.dp.toPx(),
                    textPaintStroke
                )
                it.nativeCanvas.drawText(
                    "No",
                    130.dp.toPx(),
                    0.dp.toPx(),
                    textPaint
                )
                it.nativeCanvas.drawText(
                    "Instagram",
                    50.dp.toPx(),
                    50.dp.toPx(),
                    textPaintStroke
                )
                it.nativeCanvas.drawText(
                    "Instagram",
                    50.dp.toPx(),
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
        FirstPage(navController = rememberNavController())
    }
}

