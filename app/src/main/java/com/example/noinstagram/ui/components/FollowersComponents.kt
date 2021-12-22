package com.example.noinstagram.ui.components

import androidx.compose.foundation.Image
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.example.noinstagram.data.UsersRepository
import com.example.noinstagram.ui.theme.EditProfileButtonColor
import com.example.noinstagram.utils.NavigationItem

@Composable
fun FollowersToFollowingSection(navController: NavHostController) {
    Row(
        Modifier
            .height(IntrinsicSize.Min)
            .fillMaxWidth()
            .padding(top = 15.dp, end = 30.dp),
        horizontalArrangement = Arrangement.End
    ) {
        ClickableText(
            text = AnnotatedString("Followers"),
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
            text = AnnotatedString("Following"),
            style = MaterialTheme.typography.h6.copy(),
            onClick = { navController.navigate(NavigationItem.Followers.route) },
            modifier = Modifier.padding(start = 5.dp)
        )
    }
}

@ExperimentalMaterialApi
@Composable
fun ListOfFollowers(userState: UsersRepository, currentUserUid: String?) {
    val followers = currentUserUid?.let { userState.getFollowers(it) }!!.toList()
    LazyColumn(
        Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        contentPadding = PaddingValues(top = 10.dp),
        reverseLayout = true
    ) {
        items(followers) { follower ->
            ListItem(
                text = { follower.displayName?.let { Text(text = it) } },
                icon = {
                    Box(
                        modifier = Modifier
                            .size(44.dp)
                            .background(color = Color.White, shape = CircleShape)
                            .clip(CircleShape)
                    ) {
                        Image(
                            painter = rememberImagePainter(follower.image),
                            contentDescription = null,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                },
                trailing = {
                    FloatingActionButton(
                        onClick = {},
                        backgroundColor = EditProfileButtonColor,
                        shape = RoundedCornerShape(5.dp)
                    ) {
                        Text("Follow", color = Color.White)
                    }
                    FloatingActionButton(
                        onClick = {},
                        backgroundColor = EditProfileButtonColor,
                        shape = RoundedCornerShape(5.dp)
                    ) {
                        Text("Delete", color = Color.White)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(3.dp)
                    .height(81.dp)
                    .padding(horizontal = 30.dp)
                    .clickable {
                        //navController.navigate("Profile/${person.id}")
                    },
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