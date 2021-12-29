package com.example.noinstagram.ui.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.*
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter
import com.example.noinstagram.data.PostsRepository
import com.example.noinstagram.model.Post

@Composable
fun SearchSection(
    modifier: Modifier = Modifier,
    value: String,
    label: String,
    onDoneActionClick: () -> Unit = {},
    onClearClick: () -> Unit = {},
    onFocusChanged: (FocusState) -> Unit = {},
    onValueChanged: (String) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        var value by remember {
            mutableStateOf(value)
        }
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
            value = value,
            onValueChange = {
                value = it
            },
            label = { Text(text = label) },
            textStyle = MaterialTheme.typography.subtitle1,
            singleLine = true,
            trailingIcon = {
                IconButton(onClick = {
                    onClearClick()
                    value = ""
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

@Composable
@Preview
fun SearchPreview() {
    SearchSection(modifier = Modifier, "", "Search", {}, {}, {}, {})
}

@ExperimentalFoundationApi
@Composable
@Preview
fun ExplorePostPreview() {
    ExplorePostSection(navController = rememberNavController())
}