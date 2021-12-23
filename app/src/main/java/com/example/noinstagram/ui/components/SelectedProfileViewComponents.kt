package com.example.noinstagram.ui.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.example.noinstagram.data.PostsRepository
import com.example.noinstagram.data.UsersRepository
import com.example.noinstagram.model.UserModel
import com.example.noinstagram.ui.imageview.ProfilePostView
import com.example.noinstagram.ui.imageview.RoundImage
import com.example.noinstagram.ui.theme.EditProfileButtonColor


@ExperimentalFoundationApi
@Composable
fun SelectedProfilePostSection(
    modifier: Modifier,
    postState: PostsRepository,
    navController: NavHostController,
    user: UserModel?
) {
    val selectedUserUid = user?.id
    val posts = postState.getPostsForUser(selectedUserUid!!)
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
fun SelectedProfileSection(
    modifier: Modifier,
    postState: PostsRepository,
    userState: UsersRepository,
    user: UserModel?
) {
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
            SelectedProfileStatSection(modifier = Modifier.weight(7f), userState, postState, user)
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
                        if (user != null) {
                            user.id?.let { userState.followUser(it) }
                        }
                    },
                    backgroundColor = EditProfileButtonColor,
                    shape = RoundedCornerShape(5.dp)
                ) {
                    if (user != null) {
                        if (user.id?.let { userState.userIsFollowed(it) } == true) {
                            Text("Unfollow", color = Color.White)
                        }
                    } else {
                        Text("Follow", color = Color.White)
                    }
                }
            }
        }

    }
}

@Composable
fun SelectedProfileStatSection(
    modifier: Modifier = Modifier,
    userState: UsersRepository,
    postState: PostsRepository,
    user: UserModel?
) {
    val userId = user?.id
    val postsCount = postState.getPostsForUser(userId!!).count()
    val followersCount = userState.getFollowing(userId).count()
    val followingCount = userState.getFollowers(userId).count()
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
            text = "Following"
        )
        ProfileStat(
            numberText = followersCount.toString(),
            text = "Followers"
        )
    }
}