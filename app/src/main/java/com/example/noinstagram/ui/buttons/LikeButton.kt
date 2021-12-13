package com.example.noinstagram.ui.buttons

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.contentColorFor
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.noinstagram.data.postIsLiked
import com.example.noinstagram.model.Post
import com.example.noinstagram.model.UserModel

enum class LikeAnimationState {
    Initial,
    Start,
    End
}

private const val springRatio = Spring.DampingRatioHighBouncy

@Composable
fun AnimLikeButton(
    post: Post,
    onLikeClick: (Post) -> Unit
) {

    var transitionState by remember {
        mutableStateOf(MutableTransitionState(LikeAnimationState.Initial))
    }

    Box(
        modifier = Modifier
            .clickable(
                onClick = {
                    transitionState = MutableTransitionState(LikeAnimationState.Start)
                    onLikeClick.invoke(post)
                }
            )
            .indication(
                indication = rememberRipple(bounded = false, radius = 24.dp),
                interactionSource = remember { MutableInteractionSource() }
            )
            .padding(vertical = 10.dp)
            .then(Modifier.size(30.dp)),
        contentAlignment = Alignment.Center
    ) {

        var likeIconRes by remember { mutableStateOf(com.example.noinstagram.R.drawable.ic_filled_favorite_gray) }
        val startColor = contentColorFor(MaterialTheme.colors.background)
        val endColor = Color(0xFFDF0707)

        if (transitionState.currentState == LikeAnimationState.Start) {
            transitionState.targetState = LikeAnimationState.End
        }

        val transition = updateTransition(transitionState, label = "")

        val animatedColor by transition.animateColor(
            transitionSpec = {
                when {
                    LikeAnimationState.Initial isTransitioningTo LikeAnimationState.Start ->
                        spring(dampingRatio = springRatio)
                    LikeAnimationState.Start isTransitioningTo LikeAnimationState.End ->
                        tween(200)
                    else -> snap()
                }
            }, label = ""
        ) { state ->
            when (state) {
                LikeAnimationState.Initial -> if (postIsLiked(post.id!!)) endColor else startColor
                else -> if (postIsLiked(post.id!!).not()) startColor else endColor
            }
        }

        val size by transition.animateDp(
            transitionSpec = {
                when {
                    LikeAnimationState.Initial isTransitioningTo LikeAnimationState.Start ->
                        spring(dampingRatio = springRatio)
                    LikeAnimationState.Start isTransitioningTo LikeAnimationState.End ->
                        tween(200)
                    else -> snap()
                }
            }, label = ""
        ) { state ->
            when (state) {
                LikeAnimationState.Initial -> 24.dp
                LikeAnimationState.Start -> 12.dp
                LikeAnimationState.End -> 24.dp
            }
        }

        likeIconRes = if (postIsLiked(post.id!!)) {
            com.example.noinstagram.R.drawable.ic_filled_favorite
        } else {
            com.example.noinstagram.R.drawable.ic_filled_favorite_gray
        }

        Icon(
            ImageBitmap.imageResource(id = likeIconRes), tint = animatedColor,
            modifier = Modifier.size(size),
            contentDescription = ""
        )
    }
}

@Preview
@Composable
private fun LikeButtonPreview() {
    AnimLikeButton(
        post = Post(
            id = "0",
            image = "https://source.unsplash.com/random/400x300",
            user = UserModel(
                email = "K@c.com",
                displayName = "Kamil"
            ),
            userLikes = mutableListOf("1","2"),
            timeStamp = System.currentTimeMillis() - (60000)
        ),
        onLikeClick = {
        })
}