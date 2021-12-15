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
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.example.noinstagram.R
import com.example.noinstagram.data.PostsRepository
import com.example.noinstagram.model.Post
import com.example.noinstagram.model.UserModel
import com.example.noinstagram.ui.theme.EditProfileButtonColor

@Composable
fun AddPostSection(
    modifier: Modifier = Modifier
) {
    var description by remember { mutableStateOf("") }
    var image by remember { mutableStateOf("") }
    Column(modifier = modifier.fillMaxWidth()) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
        ) {

            Spacer(modifier = Modifier.width(16.dp))
        }

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            content = {
                AddPostTextBox()

                Spacer(Modifier.height(20.dp))

                Image(
                    painter = rememberImagePainter("https://at-cdn-s01.audiotool.com/2014/01/09/documents/fat_man-Nr9zr8X/3/cover256x256-e416a9b1d3fe413d9120ca7285b3b748.jpg"),
                    contentDescription = "loaded image",
                    modifier = Modifier
                        .height(200.dp)
                        .fillMaxWidth(),
                )

                Spacer(Modifier.height(20.dp))

                Button(
                    onClick = {
                        //TO DO//
                    },
                    shape = RoundedCornerShape(6.dp),
                    modifier = Modifier
                        .width(150.dp),
                    content = {
                        Text(
                            text = "Choose photo",
                            color = Color.White
                        )
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Blue)
                )

                Spacer(Modifier.height(20.dp))

                TextField(
                    modifier = Modifier
                        .width(300.dp)
                        .padding(3.dp)
                        .shadow(elevation = 3.dp),
                    maxLines = 4,
                    value = description,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color.Black,
                        unfocusedBorderColor = Color.Black
                    ),
                    label = {
                        Text(
                            text = "Description",
                            color = Color.Gray,
                        )
                    },
                    onValueChange = {
                        description = it
                    }
                )

                Spacer(Modifier.height(20.dp))

                Button(
//                    enabled = description.isNotEmpty(),
                    onClick = {
                        //TO DO //
                    },
                    shape = RoundedCornerShape(6.dp),
                    modifier = Modifier
                        .width(150.dp),
                    content = {
                        Text(
                            text = "Add post",
                            color = Color.White,
                        )
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Blue)
                )


            }
        )

    }
}


@Composable
fun AddPostTextBox() {
    Text(
        "Add post",
        Modifier.padding(end = 10.dp),
        fontSize = 25.sp

    )
}





