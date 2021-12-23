package com.example.noinstagram.ui.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.example.noinstagram.EditProfileScreen
import com.example.noinstagram.data.PostsRepository
import com.example.noinstagram.data.UsersRepository
import com.example.noinstagram.model.Post
import com.example.noinstagram.model.UserModel
import com.example.noinstagram.ui.theme.EditProfileButtonColor

@Composable
fun ProfileSection(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    postState: PostsRepository,
    userState: UsersRepository
) {
    val user = userState.getCurrentUser()
    val minWidth = 95.dp
    val height = 30.dp
    Column(modifier = modifier.fillMaxWidth()) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
        ) {
            if (user != null) {
                RoundImage(
                    rememberImagePainter(user.image),
                    modifier = Modifier
                        .size(100.dp)
                        .weight(3f)
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            StatSection(modifier = Modifier.weight(7f), userState, postState)
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Column(modifier = Modifier.weight(2f)) {
                if (user != null) {
                    ProfileDescription(
                        displayName = user.displayName!!,
                        description = user.description!!
                    )
                }
            }
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                FloatingActionButton(
                    modifier = Modifier
                        .defaultMinSize(minWidth = minWidth)
                        .height(height),
                    onClick = {
                        navController.navigate("EditScreen")
                    },
                    backgroundColor = EditProfileButtonColor,
                    shape = RoundedCornerShape(5.dp)
                ) {
                    Text("Edit Profile", color = Color.White)
                }
            }
        }

    }
}

@Composable
fun RoundImage(
    image: Painter,
    modifier: Modifier = Modifier
) {
    Image(
        painter = image,
        contentDescription = null,
        modifier = modifier
            .aspectRatio(1f, matchHeightConstraintsFirst = true)
            .border(
                width = 1.dp,
                color = Color.LightGray,
                shape = CircleShape
            )
            .padding(3.dp)
            .clip(CircleShape),
        contentScale = ContentScale.Crop
    )
}

@Composable
fun StatSection(
    modifier: Modifier = Modifier,
    userState: UsersRepository,
    postState: PostsRepository
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround,
        modifier = modifier
    ) {
        val currentUserId = userState.getCurrentUser()?.id
        ProfileStat(
            numberText = postState.getPostsForUser(currentUserId!!).count().toString(),
            text = "Posts"
        )
        ProfileStat(
            numberText = userState.getFollowing(currentUserId).count().toString(),
            text = "Following"
        )
        ProfileStat(
            numberText = userState.getFollowers(currentUserId).count().toString(),
            text = "Followers"
        )
    }
}

@Composable
fun ProfileStat(
    numberText: String,
    text: String,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Text(
            text = numberText,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = text)
    }
}

@Composable
fun ProfileDescription(
    displayName: String,
    description: String
) {
    val letterSpacing = 0.5.sp
    val lineHeight = 20.sp
    Column(
        modifier = Modifier
            .padding(horizontal = 20.dp)
    ) {
        Text(
            text = displayName,
            fontWeight = FontWeight.Bold,
            letterSpacing = letterSpacing,
            lineHeight = lineHeight
        )
        Text(
            text = description,
            letterSpacing = letterSpacing,
            lineHeight = lineHeight,
            maxLines = 4
        )
    }
}

@ExperimentalFoundationApi
@Composable
fun PostSection(
    modifier: Modifier = Modifier,
    postState: PostsRepository,
    userState: UsersRepository,
    navController: NavHostController
) {
    val currentUserUid = userState.getCurrentUser()?.id
    val posts = postState.getPostsForUser(currentUserUid!!)
    LazyVerticalGrid(
        cells = GridCells.Fixed(3),
        modifier = modifier
            .scale(1.01f)
    ) {
        itemsIndexed(posts) { _, post ->
            ProfilePostView(post, navController)
            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}

@Composable
fun ProfilePostView(post: Post, navController: NavHostController) {
    Image(
        painter = rememberImagePainter(post.image),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .aspectRatio(1f)
            .border(
                width = 1.dp,
                color = Color.White
            )
            .padding(3.dp)
            .clip(RoundedCornerShape(10.dp))
            .clickable { navController.navigate("Post/${post.id}") }
    )
}