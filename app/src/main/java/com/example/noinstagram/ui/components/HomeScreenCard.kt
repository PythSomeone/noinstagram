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
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.example.noinstagram.R
import com.example.noinstagram.model.Post
import com.example.noinstagram.model.UserModel
import com.example.noinstagram.ui.buttons.AnimLikeButton
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin


@Composable
fun PostView(
    post: Post,
    onLikeToggle: (Post) -> Unit
) {
    Column {
        var offset by remember { mutableStateOf(Offset.Zero) }
        var zoom by remember { mutableStateOf(1f) }
        var angle by remember { mutableStateOf(0f) }
        PostHeader(post)
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
        Text(
            post.description!!,
            Modifier.padding(end = 10.dp),
            fontSize = 25.sp
        )
        Spacer(modifier = Modifier.height(1.dp))

        PostFooter(post, onLikeToggle)
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
private fun PostHeader(post: Post) {
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
                Image(
                    painter = rememberImagePainter(post.user?.image),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize()
                )
            }
            Spacer(modifier = Modifier.width(10.dp))
            Text(text = post.user!!.displayName!!,
                style = MaterialTheme.typography.subtitle2,
                modifier = Modifier.clickable { })
        }
        Icon(Icons.Filled.MoreVert, "")
    }
}

@Composable
private fun PostFooter(
    post: Post,
    onLikeToggle: (Post) -> Unit
) {
    PostFooterIconSection(post, onLikeToggle)
    PostFooterTextSection(post)
}

@Composable
private fun PostFooterIconSection(
    post: Post,
    onLikeToggle: (Post) -> Unit
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

            PostIconButton {
                Icon(ImageBitmap.imageResource(id = R.drawable.ic_outlined_comment), "")
            }

        }
    }
}

@Composable
private fun PostFooterTextSection(post: Post) {
    Column(
        modifier = Modifier.padding(
            start = 5.dp,
            end = 5.dp,
            bottom = 5.dp
        )
    ) {
        Text(
            "${post.userLikes.count()} likes",
            style = MaterialTheme.typography.subtitle2
        )

        Text(
            "View all ${post.comments.count()} comments",
            style = MaterialTheme.typography.caption
        )

        Spacer(modifier = Modifier.height(2.dp))

        Text(
            post.timeStamp!!.getTimeElapsedText(),
            style = MaterialTheme.typography.caption.copy(fontSize = 10.sp)
        )
    }
}

private fun Long.getTimeElapsedText(): String {
    val now = System.currentTimeMillis()
    val time = this

    return DateUtils.getRelativeTimeSpanString(
        time, now, 0L, DateUtils.FORMAT_ABBREV_TIME
    )
        .toString()
}

@Composable
fun PostIconButton(
    onClick: () -> Unit = { },
    icon: @Composable () -> Unit
) {
    Box(
        modifier = Modifier
            .clickable(
                onClick = onClick
            )
            .padding(vertical = 10.dp, horizontal = 5.dp)
            .indication(
                indication = rememberRipple(bounded = false, radius = 24.dp),
                interactionSource = remember { MutableInteractionSource() }
            )
            .then(Modifier.size(24.dp)),
        contentAlignment = Alignment.Center
    ) {
        icon()
    }
}

@Composable
@Preview
fun PostViewPreview() {
    PostView(
        post = Post(
            id = "0",
            image = "",
            user = UserModel(email = "abc", displayName = "kamil"),
            timeStamp = 100
        ), onLikeToggle = {})
}