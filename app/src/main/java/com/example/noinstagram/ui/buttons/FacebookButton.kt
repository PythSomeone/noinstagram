package com.example.noinstagram.ui.buttons

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import com.example.noinstagram.ui.theme.FacebookColor

@Composable
fun FacebookButton(
    text: String,
    loadingText: String = "Signing in...",
    icon: Painter,
    isFacebookLoading: Boolean = false,
    backgroundColor: Color = FacebookColor,
    progressIndicatorColor: Color = MaterialTheme.colors.primary,
    onFacebookBtnClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .clickable(
                enabled = !isFacebookLoading,
                onClick = onFacebookBtnClick
            )
            .width(250.dp),
        shape = RoundedCornerShape(24.dp),
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
                tint = Color.Unspecified,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))

            Text(text = if (isFacebookLoading) loadingText else text)
            if (isFacebookLoading) {
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

