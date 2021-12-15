package com.example.noinstagram.ui.components

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import coil.compose.rememberImagePainter
import com.example.noinstagram.R
import com.example.noinstagram.data.PostsRepository
import com.example.noinstagram.model.Post
import com.example.noinstagram.model.UserModel
import com.example.noinstagram.ui.theme.AddPostButtonColor
import com.example.noinstagram.ui.theme.ChoosePhotoButtonColor
import com.example.noinstagram.viewmodel.PostViewModel
import java.util.Objects.isNull
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.noinstagram.utils.NavigationItem

@Composable
fun AddPostSection(
    user: UserModel,
    modifier: Modifier = Modifier,
    viewModel: PostViewModel = viewModel(),
) {
    var description by remember { mutableStateOf("") }
    var imageUri by remember {
        mutableStateOf<Uri?>(null)
    }
    val bitmap =  remember {
        mutableStateOf<Bitmap?>(null)
    }
    val launcher = rememberLauncherForActivityResult(contract =
    ActivityResultContracts.GetContent()) { uri: Uri? ->
        imageUri = uri
    }
    val context = LocalContext.current
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

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    content = {
                        imageUri?.let {
                            if (Build.VERSION.SDK_INT < 28) {
                                bitmap.value = MediaStore.Images
                                    .Media.getBitmap(context.contentResolver,it)

                            } else {
                                val source = ImageDecoder
                                    .createSource(context.contentResolver,it)
                                bitmap.value = ImageDecoder.decodeBitmap(source)
                            }
                            bitmap.value?.let {  btm ->
                                Image(bitmap = btm.asImageBitmap(),
                                    contentDescription =null,
                                    modifier = Modifier.size(400.dp))
                            }
                        }
                    }
                )


                Spacer(Modifier.height(20.dp))

                Button(
                    onClick = {
                        launcher.launch("image/*")
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
                    colors = ButtonDefaults.buttonColors(backgroundColor = ChoosePhotoButtonColor)
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
                    enabled= imageUri.toString().isNotEmpty(),
                    onClick = {
                        viewModel.uploadImage(imageUri.toString(), user)
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
                    colors = ButtonDefaults.buttonColors(backgroundColor = AddPostButtonColor)
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





