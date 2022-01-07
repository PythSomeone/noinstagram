package com.example.noinstagram.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.example.noinstagram.data.UsersRepository
import com.example.noinstagram.ui.imageview.RoundImage
import com.example.noinstagram.ui.theme.EditProfileButtonColor
import com.example.noinstagram.viewmodel.FollowViewModel


@ExperimentalMaterialApi
@Composable
fun ListOfSelectedProfileFollowers(
    uid: String?,
    navController: NavHostController,
    followViewModel: FollowViewModel = viewModel()
) {
    followViewModel.getFollowers(uid!!)
    val followers = followViewModel.followers.value
    LazyColumn(
        Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        contentPadding = PaddingValues(top = 10.dp),
        reverseLayout = true
    ) {
        items(followers.distinct()) { follower ->
            ListItem(
                text = {
                    follower.displayName?.let {
                        Text(
                            text = it,
                            modifier = Modifier.clickable { navController.navigate("PublicProfile/${follower.id}") }
                        )
                    }

                },
                icon = {
                    Box(
                        modifier = Modifier
                            .size(44.dp)
                            .background(color = Color.White, shape = CircleShape)
                            .clip(CircleShape)
                    ) {
                        RoundImage(
                            rememberImagePainter(follower.image),
                            modifier = Modifier
                                .fillMaxSize()
                                .clickable {
                                    navController.navigate("PublicProfile/${follower.id}")
                                }
                        )
                    }
                },
                trailing = {
                    Row {
                        val currentUserUid = UsersRepository.getCurrentUser()?.id
                        if (follower.id != currentUserUid) {
                            FloatingActionButton(
                                onClick = {
                                    followViewModel.followUser(
                                        follower.id!!,
                                        currentUserUid!!
                                    )
                                    followViewModel.checkIsFollowed(currentUserUid, follower.id!!)
                                },
                                backgroundColor = EditProfileButtonColor,
                                shape = RoundedCornerShape(5.dp)
                            ) {
                                Text(
                                    text = followViewModel.isFollowedText.collectAsState().value,
                                    color = Color.White
                                )
                            }
                        }
                    }

                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(3.dp)
                    .height(81.dp)
                    .padding(horizontal = 30.dp),
            )
            Divider(
                color = Color.Black,
                modifier = Modifier
                    .fillMaxWidth()
                    .width(1.dp)
            )

        }
    }
}

@Composable
fun FollowersToFollowingSectionSelectedProfile(navController: NavHostController, uid: String?) {
    Row(
        Modifier
            .height(IntrinsicSize.Min)
            .fillMaxWidth()
            .padding(top = 15.dp, end = 30.dp),
        horizontalArrangement = Arrangement.End
    ) {
        Text(
            text = AnnotatedString("Followers"),
            style = MaterialTheme.typography.h5.copy(),
            modifier = Modifier.padding(end = 5.dp)
        )

        Divider(
            color = Color.Black,
            modifier = Modifier
                .fillMaxHeight()
                .width(1.dp)
        )

        ClickableText(
            text = AnnotatedString("Following"),
            style = MaterialTheme.typography.h6.copy(),
            onClick = { navController.navigate("SelectedProfileFollowing/$uid") },
            modifier = Modifier.padding(start = 5.dp)
        )
    }
}