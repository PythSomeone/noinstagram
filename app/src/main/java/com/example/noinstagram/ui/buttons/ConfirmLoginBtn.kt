package com.example.noinstagram.ui.buttons

import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExtendedFloatingActionButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ConfirmLoginBtn() {
    ExtendedFloatingActionButton(
        onClick = { /* ... */ },
        shape = RoundedCornerShape(24.dp),
        modifier = Modifier
            .width(300.dp)
            .alpha(0.4f),
        text = { Text("Login") },
        backgroundColor = Color.White,

        )
}