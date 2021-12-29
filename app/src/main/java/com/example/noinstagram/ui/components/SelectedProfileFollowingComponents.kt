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
fun ListOfFollowingSelectedProfile(
    navController: NavHostController,
    uid: String?,
    followViewModel: FollowViewModel = viewModel()
) {
    followViewModel.getFollowing(uid!!)
    val following = followViewModel.following
    LazyColumn(
        Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        contentPadding = PaddingValues(top = 10.dp),
        reverseLayout = true
    ) {
        items(following.value) { following ->
            ListItem(
                text = {
                    following.displayName?.let {
                        Text(
                            text = it,
                            modifier = Modifier.clickable { navController.navigate("PublicProfile/${following.id}") }
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
                            rememberImagePainter(following.image),
                            modifier = Modifier
                                .fillMaxSize()
                                .clickable {
                                    navController.navigate("PublicProfile/${following.id}")
                                }
                        )
                    }
                },
                trailing = {
                    val currentUserUid = UsersRepository.getCurrentUser()?.id
                    if (following.id != currentUserUid) {
                        FloatingActionButton(
                            onClick = {
                                following.id?.let {
                                    followViewModel.followUser(
                                        currentUserUid!!,
                                        it
                                    )
                                }
                                following.id?.let {
                                    followViewModel.checkIsFollowed(
                                        currentUserUid!!,
                                        following.id!!
                                    )
                                }
                            },
                            backgroundColor = EditProfileButtonColor,
                            shape = RoundedCornerShape(5.dp)
                        ) {
                            followViewModel.checkIsFollowed(currentUserUid!!, following.id!!)
                            Text(
                                followViewModel.isFollowedText.collectAsState().value,
                                color = Color.White
                            )
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
fun FollowingToFollowersSectionSelectedProfile(navController: NavHostController, uid: String?) {
    Row(
        Modifier
            .height(IntrinsicSize.Min)
            .fillMaxWidth()
            .padding(top = 15.dp, end = 30.dp),
        horizontalArrangement = Arrangement.End
    ) {
        ClickableText(
            text = AnnotatedString("Following"),
            style = MaterialTheme.typography.h5.copy(),
            onClick = {},
            modifier = Modifier.padding(end = 5.dp)
        )

        Divider(
            color = Color.Black,
            modifier = Modifier
                .fillMaxHeight()
                .width(1.dp)
        )

        ClickableText(
            text = AnnotatedString("Followers"),
            style = MaterialTheme.typography.h6.copy(),
            onClick = { navController.navigate("SelectedProfileFollowers/$uid") },
            modifier = Modifier.padding(start = 5.dp)
        )
    }
}