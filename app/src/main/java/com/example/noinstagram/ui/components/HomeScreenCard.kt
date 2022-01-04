package com.example.noinstagram.ui.components

import android.text.format.DateUtils
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.example.noinstagram.R
import com.example.noinstagram.data.UsersRepository
import com.example.noinstagram.model.CommentModel
import com.example.noinstagram.model.Post
import com.example.noinstagram.ui.buttons.AnimLikeButton
import com.example.noinstagram.ui.imageview.RoundImage
import com.example.noinstagram.viewmodel.PostViewModel
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin


@ExperimentalMaterialApi
@Composable
fun PostView(
    post: Post,
    onLikeToggle: (Post) -> Unit,
    navController: NavHostController,
    refreshing: MutableState<Boolean>
) {
    Column {
        var offset by remember { mutableStateOf(Offset.Zero) }
        var zoom by remember { mutableStateOf(1f) }
        var angle by remember { mutableStateOf(0f) }
        PostHeader(post, navController)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .background(color = Color.Transparent)
                .padding(10.dp)
        ) {
            Image(
                painter = rememberImagePainter(post.image),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RectangleShape)
                    .pointerInput(Unit) {
                        detectTransformGestures(
                            onGesture = { centroid, pan, gestureZoom, gestureRotate ->
                                val oldScale = zoom
                                val newScale = zoom * gestureZoom
                                if (oldScale != newScale) {
                                    offset =
                                        (offset + centroid / oldScale).rotateBy(gestureRotate) -
                                                (centroid / newScale + pan / oldScale)
                                    zoom = newScale
                                    angle += gestureRotate
                                }
                            }
                        )
                    }
                    .graphicsLayer {
                        translationX = -offset.x * zoom
                        translationY = -offset.y * zoom
                        scaleX = zoom
                        scaleY = zoom
                        rotationZ = angle
                        transformOrigin = TransformOrigin(0f, 0f)
                    }

            )

        }
        Spacer(modifier = Modifier.height(1.dp))

        PostFooter(post, onLikeToggle, refreshing, navController)
        Divider()
    }
}

fun Offset.rotateBy(angle: Float): Offset {
    val angleInRadians = angle * PI / 180
    return Offset(
        (x * cos(angleInRadians) - y * sin(angleInRadians)).toFloat(),
        (x * sin(angleInRadians) + y * cos(angleInRadians)).toFloat()
    )
}

@Composable
private fun PostHeader(post: Post, navController: NavHostController) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .background(color = Color.White, shape = CircleShape)
                    .clip(CircleShape)
            ) {
                RoundImage(
                    rememberImagePainter(post.user?.image),
                    modifier = Modifier
                        .fillMaxSize()
                        .clickable { navController.navigate("PublicProfile/${post.user?.id}") }
                )
            }
            Spacer(modifier = Modifier.width(10.dp))
            Text(text = post.user!!.displayName!!,
                style = MaterialTheme.typography.subtitle2,
                modifier = Modifier.clickable { navController.navigate("PublicProfile/${post.user.id}") })
        }
        Icon(Icons.Filled.MoreVert, "")
    }
}

@ExperimentalMaterialApi
@Composable
private fun PostFooter(
    post: Post,
    onLikeToggle: (Post) -> Unit,
    refreshing: MutableState<Boolean>,
    navController: NavHostController
) {
    PostFooterIconSection(post, onLikeToggle, refreshing)
    PostFooterTextSection(post, navController)
}

@Composable
private fun PostFooterIconSection(
    post: Post,
    onLikeToggle: (Post) -> Unit,
    refreshing: MutableState<Boolean>
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 5.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            AnimLikeButton(post, onLikeToggle)

            PostIconButton(post, refreshing)
        }
    }
}

@ExperimentalMaterialApi
@Composable
private fun PostFooterTextSection(post: Post, navController: NavHostController) {
    Column(
        modifier = Modifier.padding(
            start = 5.dp,
            end = 5.dp,
            bottom = 5.dp
        )
    ) {
        val showComments = remember { mutableStateOf(false) }
        Text(
            text = "${post.user?.displayName}: ${post.description!!}",
            Modifier.padding(end = 10.dp),
            style = MaterialTheme.typography.subtitle2
        )
        Text(
            "${post.userLikes.count()} likes",
            style = MaterialTheme.typography.subtitle2
        )

        Text(
            "View all ${post.comments.count()} comments",
            style = MaterialTheme.typography.caption,
            modifier = Modifier.clickable { showComments.value = true }
        )

        if (showComments.value) {
            CommentColumn(post.comments, navController)
        }

        Spacer(modifier = Modifier.height(2.dp))

        Text(
            post.timeStamp!!.getTimeElapsedText(),
            style = MaterialTheme.typography.caption.copy(fontSize = 10.sp)
        )
    }
}

fun Long.getTimeElapsedText(): String {
    val now = System.currentTimeMillis()
    val time = this

    return DateUtils.getRelativeTimeSpanString(
        time, now, 0L, DateUtils.FORMAT_ABBREV_TIME
    )
        .toString()
}

@Composable
fun PostIconButton(
    post: Post,
    refreshing: MutableState<Boolean>,
    onClick: () -> Unit = { },
    viewModel: PostViewModel = viewModel(),
) {
    val openAddCommentDialog = remember { mutableStateOf(false) }
    var comment by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .clickable(
                onClick = { openAddCommentDialog.value = true }
            )
            .padding(vertical = 10.dp, horizontal = 5.dp)
            .indication(
                indication = rememberRipple(bounded = false, radius = 24.dp),
                interactionSource = remember { MutableInteractionSource() }
            )
            .then(Modifier.size(24.dp)),
        contentAlignment = Alignment.Center
    ) {
        Icon(ImageBitmap.imageResource(id = R.drawable.ic_outlined_comment), "")
    }

    if (openAddCommentDialog.value) {
        AlertDialog(onDismissRequest = { openAddCommentDialog.value = false },
            title = { Text(text = "Add Comment") },
            text = {
                OutlinedTextField(
                    value = comment,
                    onValueChange = { comment = it },
                    placeholder = { Text("Comment...") }
                )
            },
            confirmButton = {
                Button(onClick = {
                    val commentModel = CommentModel(
                        text = comment,
                        user = UsersRepository.getCurrentUser(),
                        timeStamp = System.currentTimeMillis()
                    )
                    if (comment.isNotEmpty()) {
                        viewModel.addComment(post, commentModel)
                    }
                    refreshing.value = true
                    openAddCommentDialog.value = false
                }) {
                    Text("Confirm")
                }
            })
    }
}