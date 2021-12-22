package com.example.noinstagram

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.example.noinstagram.data.UsersRepository
import com.example.noinstagram.ui.theme.NoInstagramTopBarTextColor

@Composable
fun TopAppBar(
    backgroundColor: Color,
    title: String
) {
    val userState = remember { UsersRepository }
    val expanded = remember { mutableStateOf(false) }
    TopAppBar(
        backgroundColor = backgroundColor,
        modifier = Modifier.fillMaxWidth(),
        elevation = 10.dp
    ) {
        //title
        Box(Modifier.height(32.dp)) {

            //Title
            Row(
                Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically
            ) {

                ProvideTextStyle(value = MaterialTheme.typography.h6) {
                    CompositionLocalProvider(
                        LocalContentAlpha provides ContentAlpha.high,
                    ) {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            maxLines = 1,
                            text = title,
                            color = NoInstagramTopBarTextColor
                        )
                    }
                }
            }
            //        actions
            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                Row(
                    Modifier.fillMaxHeight(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically,
                    content = {
                        Box(
                            modifier = Modifier
                                .size(44.dp)
                                .background(color = Color.White, shape = CircleShape)
                                .clip(CircleShape)
                        ) {
                            Image(
                                painter = rememberImagePainter(userState.getCurrentUser()?.image),
                                contentDescription = null,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clickable { expanded.value = true },
                                contentScale = ContentScale.Crop
                            )
                            DropdownMenu(
                                expanded = expanded.value,
                                onDismissRequest = { expanded.value = false },
                            ) {
                                DropdownMenuItem(onClick = {
                                    expanded.value = false
                                }) {
                                    Icon(
                                        ImageVector.vectorResource(R.drawable.ic_baseline_logout_24),
                                        "Logout"
                                    )
                                    Text("Log out")
                                }
                            }
                        }
                    }
                )
            }
        }
    }
}