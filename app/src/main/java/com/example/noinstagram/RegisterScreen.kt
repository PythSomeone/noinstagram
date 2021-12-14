package com.example.noinstagram

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.noinstagram.ui.canva.NoInstagramCanvas
import com.example.noinstagram.viewmodel.RegisterViewModel



@Composable
fun RegisterScreen(navController: NavController, viewModel: RegisterViewModel = viewModel()) {
    var userName by remember { mutableStateOf("") }
    var userEmail by remember { mutableStateOf("") }
    var userPassword by remember { mutableStateOf("") }
    var passwordVisibility by remember { mutableStateOf(false) }
    val icon = if (passwordVisibility)
        painterResource(id = R.drawable.ic_visibility_on)
    else
        painterResource(id = R.drawable.ic_visibility_off)


    Surface(
        color = MaterialTheme.colors.primary,
        modifier = Modifier
            .fillMaxSize()
    ) {
        RegisterScreenBackground()

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            content = {
                Spacer(Modifier.height(70.dp))
                NoInstagramCanvas()

                Spacer(Modifier.height(70.dp))
                RegisterAccountTextBox()

                Spacer(Modifier.height(20.dp))

                OutlinedTextField(
                    modifier = Modifier.width(300.dp),
                    value = userName,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color.White,
                        unfocusedBorderColor = Color.White
                    ),
                    shape = RoundedCornerShape(24.dp),
                    label = {
                        Text(
                            text = "Name",
                            color = Color.White,
                            modifier = Modifier.padding(start = 20.dp)
                        )
                    },
                    onValueChange = {
                        userName = it
                    }
                )

                Spacer(Modifier.height(20.dp))

                OutlinedTextField(
                    modifier = Modifier.width(300.dp),
                    value = userEmail,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color.White,
                        unfocusedBorderColor = Color.White
                    ),
                    shape = RoundedCornerShape(24.dp),
                    label = {
                        Text(
                            text = "Email",
                            color = Color.White,
                            modifier = Modifier.padding(start = 20.dp)
                        )
                    },
                    onValueChange = {
                        userEmail = it
                    }
                )

                Spacer(Modifier.height(20.dp))

                OutlinedTextField(
                    value = userPassword,
                    onValueChange = {
                        userPassword = it
                    },
                    modifier = Modifier.width(300.dp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color.White,
                        unfocusedBorderColor = Color.White
                    ),
                    shape = RoundedCornerShape(24.dp),
                    label = {
                        Text(
                            text = "Password",
                            color = Color.White,
                            modifier = Modifier.padding(start = 20.dp)
                        )
                    },
                    trailingIcon = {
                        IconButton(onClick = {
                            passwordVisibility = !passwordVisibility
                        }) {
                            Icon(
                                painter = icon,
                                contentDescription = "Visibility icon"
                            )
                        }
                    },
                    visualTransformation = if (passwordVisibility) VisualTransformation.None
                    else PasswordVisualTransformation()


                )

                Spacer(Modifier.height(20.dp))

                Button(
                    enabled = userEmail.isNotEmpty() && userPassword.isNotEmpty(),
                    onClick = {
                        viewModel.createUserWithEmailAndPassword(
                            userName.trim(),
                            userEmail.trim(),
                            userPassword.trim()
                        )
                    },
                    shape = RoundedCornerShape(24.dp),
                    modifier = Modifier
                        .width(300.dp)
                        .alpha(0.4f),
                    content = {
                        Text(text = "Register")
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.White)
                )

                Text(
                    text = "Already have an account?",
                    fontSize = 16.sp,
                    color = Color.White,
                    fontFamily = FontFamily(Font(R.font.verdana))
                )
                Spacer(Modifier.height(10.dp))
                TextButton(onClick = { navController.navigate("LoginPage") }) {
                    Text(
                        text = "Click here",
                        fontSize = 16.sp,
                        color = Color.Black,
                        fontFamily = FontFamily(Font(R.font.verdana))
                    )
                }
            }
        )
    }

}


@Preview(showBackground = true)
@Composable
fun RegisterScreenPreview() {
    RegisterScreen(navController = rememberNavController())
}

@Composable
fun RegisterAccountTextBox() {
    Text(
        text = "Register your account",
        fontSize = 20.sp,
        color = Color.White,
        fontFamily = FontFamily(Font(R.font.verdana))
    )
}

@Composable
fun RegisterScreenBackground() {
    Image(
        painterResource(id = R.mipmap.ic_first_page_background_foreground),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier.fillMaxHeight(),
    )
}