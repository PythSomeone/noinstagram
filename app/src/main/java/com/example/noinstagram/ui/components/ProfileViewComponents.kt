package com.example.noinstagram.ui.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.example.noinstagram.data.PostsRepository
import com.example.noinstagram.data.UsersRepository
import com.example.noinstagram.ui.imageview.ProfilePostView
import com.example.noinstagram.ui.imageview.RoundImage
import com.example.noinstagram.ui.theme.EditProfileButtonColor
import com.example.noinstagram.utils.NavigationItem

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
            StatSection(modifier = Modifier.weight(7f), userState, postState, navController)
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
fun StatSection(
    modifier: Modifier = Modifier,
    userState: UsersRepository,
    postState: PostsRepository,
    navController: NavHostController
) {
    val currentUserId = userState.getCurrentUser()?.id
    val postsCount by remember {
        mutableStateOf(
            postState.getPostsForUser(currentUserId!!).count()
        )
    }
    val followersCount by remember {
        mutableStateOf(
            userState.getFollowing(currentUserId!!).count()
        )
    }
    val followingCount by remember {
        mutableStateOf(
            userState.getFollowers(currentUserId!!).count()
        )
    }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround,
        modifier = modifier
    ) {

        ProfileStat(
            numberText = postsCount.toString(),
            text = "Posts"
        )
        ProfileStat(
            numberText = followingCount.toString(),
            text = "Following", modifier = Modifier.clickable {
                navController.navigate(NavigationItem.Followers.route)
            }
        )
        ProfileStat(
            numberText = followersCount.toString(),
            text = "Followers",
            modifier = Modifier.clickable {
                navController.navigate("FollowersPage")
            }
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
