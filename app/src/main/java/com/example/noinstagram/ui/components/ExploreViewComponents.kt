package com.example.noinstagram.ui.components

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.example.noinstagram.data.PostsRepository
import com.example.noinstagram.data.UsersRepository
import com.example.noinstagram.model.Post
import com.example.noinstagram.model.UserModel
import java.util.*

@Composable
fun SearchSection(
    modifier: Modifier = Modifier,
    textValue: MutableState<TextFieldValue>,
    label: String,
    onDoneActionClick: () -> Unit = {},
    onClearClick: () -> Unit = {},
    onFocusChanged: (FocusState) -> Unit = {},
    onValueChanged: (String) -> Unit,
    navController: NavHostController
) {
    var state by remember {
        mutableStateOf(false)
    }
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Text(
            "Explore",
            Modifier.padding(end = 10.dp),
            fontSize = 25.sp
        )
        OutlinedTextField(
            modifier = modifier
                .fillMaxWidth(.6f)
                .onFocusChanged { onFocusChanged(it) }
                .align(CenterVertically),
            value = textValue.value,
            onValueChange = { value ->
                textValue.value = value
                state = true
            },
            label = { Text(text = label) },
            textStyle = MaterialTheme.typography.subtitle1,
            singleLine = true,
            trailingIcon = {
                IconButton(onClick = {
                    onClearClick()
                    textValue.value = TextFieldValue("")
                    state = false
                }) {
                    Icon(imageVector = Icons.Filled.Clear, contentDescription = "Clear")
                }
            },
            keyboardActions = KeyboardActions(onDone = { onDoneActionClick() }),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Text
            ),
            shape = RoundedCornerShape(100f),
        )
    }
    if (state) {
        ProfilesInSearch(state = textValue, navController)
    }
}

@Composable
fun ProfilesInSearch(state: MutableState<TextFieldValue>, navController: NavHostController) {
    var filteredProfiles: List<UserModel>
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 50.dp)
    ) {
        filteredProfiles = if (state.value.text.isEmpty()) {
            UsersRepository.users.value
        } else {
            val resultList: MutableList<UserModel> = mutableListOf()
            for (user in UsersRepository.users.value) {
                if (user.displayName!!.lowercase(Locale.getDefault())
                        .contains(state.value.text.lowercase(Locale.getDefault()))
                    || user.description!!.lowercase(Locale.getDefault())
                        .contains(
                            state.value.text.lowercase(Locale.getDefault())
                        )
                ) {
                    resultList.add(user)
                }
            }
            resultList
        }
        items(filteredProfiles) { filteredProfile ->
            ProfileListItem(
                id = filteredProfile.id!!,
                navController
            )
            Divider(color = Color.Black)
        }
    }
}

@Composable
fun ProfileListItem(id: String, navController: NavHostController) {
    val user = UsersRepository.getUser(id)
    Row(
        modifier = Modifier
            .clickable(onClick = { navController.navigate("PublicProfile/${id}") })
            .height(57.dp)
            .fillMaxWidth()
            .padding(PaddingValues(8.dp, 16.dp))
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Row(Modifier.fillMaxWidth(), Arrangement.Start) {
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .background(color = Color.White, shape = CircleShape)
                        .clip(CircleShape)
                ) {
                    com.example.noinstagram.ui.imageview.RoundImage(
                        rememberImagePainter(user?.image),
                        modifier = Modifier
                            .fillMaxSize()
                    )
                }
                Text(text = user?.displayName!!, fontSize = 18.sp, textAlign = TextAlign.Center)
            }
        }

    }
}

@ExperimentalFoundationApi
@Composable
fun ExplorePostSection(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val posts by PostsRepository.posts
    LazyVerticalGrid(
        cells = GridCells.Fixed(2),
        modifier = modifier
            .scale(1.01f)
    ) {
        itemsIndexed(posts.asReversed()) { _, post ->
            ExplorePostView(post, navController)
            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}

@Composable
fun ExplorePostView(post: Post, navController: NavHostController) {
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
            .padding(10.dp)
            .clip(RoundedCornerShape(10.dp))
            .clickable { navController.navigate("Post/${post.id}") }
    )
}