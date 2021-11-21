package com.example.noinstagram.ui.canva

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.noinstagram.R
import com.example.noinstagram.ui.theme.LightBlue

@Composable
fun NoInstagramCanvas() {
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