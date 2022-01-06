package com.example.noinstagram.ui.components

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.noinstagram.model.UserModel
import com.example.noinstagram.ui.theme.AddPostButtonColor
import com.example.noinstagram.ui.theme.ChoosePhotoButtonColor
import com.example.noinstagram.utils.NavigationItem
import com.example.noinstagram.viewmodel.ProfileViewModel

@Composable
fun EditProfileSection(
    user: UserModel,
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: ProfileViewModel = viewModel(),

    ) {
    var description by remember { mutableStateOf(user.description) }
    var displayName by remember { mutableStateOf(user.displayName) }
    var imageUri by remember {
        mutableStateOf<Uri?>(null)
    }
    val bitmap = remember {
        mutableStateOf<Bitmap?>(null)
    }
    val launcher = rememberLauncherForActivityResult(
        contract =
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
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
                EditProfileTextBox()

                Spacer(Modifier.height(20.dp))
                if ((imageUri != null)) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(150.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        content = {
                            imageUri?.let {
                                if (Build.VERSION.SDK_INT < 28) {
                                    bitmap.value = MediaStore.Images
                                        .Media.getBitmap(context.contentResolver, it)

                                } else {
                                    val source = ImageDecoder
                                        .createSource(context.contentResolver, it)
                                    bitmap.value = ImageDecoder.decodeBitmap(source)
                                }
                                bitmap.value?.let { btm ->
                                    RoundImage(
                                        btm,
                                        modifier = modifier
                                            .aspectRatio(1f, matchHeightConstraintsFirst = true)
                                            .border(
                                                width = 1.dp,
                                                color = Color.LightGray,
                                                shape = CircleShape
                                            )
                                            .padding(3.dp)
                                            .clip(CircleShape)
                                            .height(100.dp)
                                    )
                                }
                            }
                        }
                    )
                } else {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(150.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        content = {
                            Image(
                                painter = rememberImagePainter(user.image),
                                contentDescription = null,
                                modifier = modifier
                                    .aspectRatio(1f, matchHeightConstraintsFirst = true)
                                    .border(
                                        width = 1.dp,
                                        color = Color.LightGray,
                                        shape = CircleShape
                                    )
                                    .padding(3.dp)
                                    .clip(CircleShape)
                                    .height(100.dp)
                            )
                        }
                    )


                }


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
                    value = displayName!!,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color.Black,
                        unfocusedBorderColor = Color.Black
                    ),
                    label = {
                        Text(
                            text = "Change name",
                            color = Color.Gray,
                        )
                    },
                    onValueChange = {
                        displayName = it
                    }
                )

                TextField(
                    modifier = Modifier
                        .width(300.dp)
                        .padding(3.dp)
                        .shadow(elevation = 3.dp),
                    maxLines = 4,
                    value = description!!,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color.Black,
                        unfocusedBorderColor = Color.Black
                    ),
                    label = {
                        Text(
                            text = "Profile description",
                            color = Color.Gray,
                        )
                    },
                    onValueChange = {
                        description = it
                    }
                )

                Spacer(Modifier.height(20.dp))

                Button(
                    onClick = {
                        val uriExists = imageUri != null
                        if (uriExists) {
                            viewModel.changeData(user, description!!, displayName!!, imageUri!!)
                        } else
                            viewModel.changeData(user, description!!, displayName!!)
                        Toast.makeText(
                            context,
                            "Data changed...",
                            Toast.LENGTH_SHORT
                        ).show()
                        navController.navigate(NavigationItem.Profile.route)
                    },
                    shape = RoundedCornerShape(6.dp),
                    modifier = Modifier
                        .width(150.dp),
                    content = {
                        Text(
                            text = "Save",
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
fun EditProfileTextBox() {
    Text(
        "Edit profile",
        Modifier.padding(end = 10.dp),
        fontSize = 25.sp

    )
}

@Composable
fun RoundImage(
    bitmap: Bitmap,
    modifier: Modifier = Modifier
) {
    Image(
        bitmap = bitmap.asImageBitmap(),
        contentDescription = null,
        modifier = modifier
            .aspectRatio(1f, matchHeightConstraintsFirst = true)
            .border(
                width = 1.dp,
                color = Color.LightGray,
                shape = CircleShape
            )
            .padding(3.dp)
            .clip(CircleShape)
    )
}







