package com.example.noinstagram.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.example.noinstagram.model.CommentModel

@ExperimentalMaterialApi
@Composable
fun CommentColumn(comments: MutableList<CommentModel>, navController: NavHostController) {
    Column(verticalArrangement = Arrangement.spacedBy((-5).dp)) {
        for (comment in comments.asReversed()) {
            ListItem(
                text = {
                    Row {
                        Box(
                            modifier = Modifier
                                .size(20.dp)
                                .background(color = Color.White, shape = CircleShape)
                                .clip(CircleShape)
                        ) {
                            com.example.noinstagram.ui.imageview.RoundImage(
                                rememberImagePainter(comment.user?.image),
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clickable {
                                        navController.navigate("PublicProfile/${comment.user?.id}")
                                    }
                            )
                        }
                        Spacer(modifier = Modifier.width(20.dp))
                        Text(
                            modifier = Modifier
                                .clickable { navController.navigate("PublicProfile/${comment.user?.id}") },
                            text = comment.user?.displayName!! + ": ",
                            style = MaterialTheme.typography.body2
                        )
                        Text(comment.text!!, style = MaterialTheme.typography.body2)
                    }
                },
                secondaryText = {
                    Text(
                        text = comment.timeStamp!!.getTimeElapsedText(),
                        style = MaterialTheme.typography.caption.copy(fontSize = 10.sp)
                    )
                }
            )
            Divider(Modifier.height(0.2.dp), color = Color.Black)
        }
    }
}
