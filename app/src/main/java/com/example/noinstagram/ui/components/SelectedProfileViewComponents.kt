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
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.example.noinstagram.data.PostsRepository
import com.example.noinstagram.data.UsersRepository
import com.example.noinstagram.model.UserModel
import com.example.noinstagram.ui.imageview.ProfilePostView
import com.example.noinstagram.ui.imageview.RoundImage
import com.example.noinstagram.ui.theme.EditProfileButtonColor
import com.example.noinstagram.viewmodel.FollowViewModel


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
    user: UserModel?,
    followViewModel: FollowViewModel = viewModel()
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
            RoundImage(
                rememberImagePainter(user?.image),
                modifier = Modifier
                    .size(100.dp)
                    .weight(3f)
            )
            Spacer(modifier = Modifier.width(16.dp))
            SelectedProfileStatSection(
                modifier = Modifier.weight(7f),
                postState = postState,
                user = user
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Column(modifier = Modifier.weight(2f)) {
                ProfileDescription(
                    displayName = user?.displayName!!,
                    description = user.description!!
                )
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
                        user?.id?.let {
                            followViewModel.followUser(
                                UsersRepository.getCurrentUser()?.id!!,
                                user.id!!
                            )
                        }
                        followViewModel.getFollowersCount(user?.id!!)
                        followViewModel.getFollowingCount(user.id!!)
                        followViewModel.checkIsFollowed(user.id!!)
                    },
                    backgroundColor = EditProfileButtonColor,
                    shape = RoundedCornerShape(5.dp)
                ) {
                    followViewModel.checkIsFollowed(user?.id!!)
                    Text(
                        text = followViewModel.isFollowedText.collectAsState().value,
                        color = Color.White
                    )
                }
            }
        }

    }
}

@Composable
fun SelectedProfileStatSection(
    modifier: Modifier = Modifier,
    postState: PostsRepository,
    user: UserModel?,
    followViewModel: FollowViewModel = viewModel()
) {
    followViewModel.getFollowersCount(user?.id!!)
    followViewModel.getFollowingCount(user.id!!)
    val userId = user.id
    val postsCount = postState.getPostsForUser(userId!!).count()
    val followersCount = followViewModel.followersCount.collectAsState().value
    val followingCount = followViewModel.followingCount.collectAsState().value
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
            numberText = followingCount,
            text = "Following"
        )
        ProfileStat(
            numberText = followersCount,
            text = "Followers"
        )
    }
}