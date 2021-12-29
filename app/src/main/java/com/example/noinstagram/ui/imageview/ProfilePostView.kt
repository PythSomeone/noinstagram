package com.example.noinstagram.ui.imageview

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.example.noinstagram.model.Post

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