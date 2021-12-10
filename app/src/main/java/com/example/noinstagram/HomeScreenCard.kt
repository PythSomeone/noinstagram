package com.example.noinstagram

import android.text.format.DateUtils
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.example.noinstagram.model.Post
import com.example.noinstagram.model.UserModel
import com.example.noinstagram.ui.buttons.AnimLikeButton


@Composable
fun PostView(
    post: Post,
    onLikeToggle: (Post) -> Unit
) {
    Column {
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
                    .clip(RoundedCornerShape(10.dp))
            )
        }
        PostFooter(post, onLikeToggle)
        Divider()
    }
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
                    painter = rememberImagePainter("https://s.gravatar.com/avatar/62a968f41c1feb83fd1cd142e7c043f3?s=200"),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize()
                )
            }
            Spacer(modifier = Modifier.width(10.dp))
            Text(text = post.user!!.displayName!!, style = MaterialTheme.typography.subtitle2)
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
            "${post.likesCount} likes",
            style = MaterialTheme.typography.subtitle2
        )

        Text(
            "View all ${post.commentsCount} comments",
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
            isLiked = true,
            likesCount = 3,
            commentsCount = 2,
            timeStamp = 100
        ), onLikeToggle = {})
}