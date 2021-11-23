package com.example.noinstagram

import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.ExitToApp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import com.example.noinstagram.R
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.noinstagram.ui.canva.NoInstagramCanvas
import com.example.noinstagram.utils.LoadingState
import com.example.noinstagram.viewmodel.AuthViewModel
import com.example.noinstagram.viewmodel.LoginViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase




@Composable
fun LoginScreen(navController: NavController, viewModel: LoginViewModel = viewModel()) {

    var userEmail by remember { mutableStateOf("") }
    var userPassword by remember { mutableStateOf("") }

    var passwordVisibility by remember { mutableStateOf(false) }
    val icon = if(passwordVisibility)
        painterResource(id = com.example.noinstagram.R.drawable.ic_visibility_on)
    else
        painterResource(id = com.example.noinstagram.R.drawable.ic_visibility_off)

    val state by viewModel.loadingState.collectAsState()



    Surface(
        color = MaterialTheme.colors.primary,
        modifier = Modifier
            .fillMaxSize()
    ) {
        LoginScreenBackground()




        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            content = {
                Spacer(Modifier.height(70.dp))
                NoInstagramCanvas()

//                IconButton(onClick = { FirebaseAuth.getInstance().signOut() }) {
//                    Icon(
//                        imageVector = Icons.Rounded.ExitToApp,
//                        contentDescription = null,
//                    )
//                }
                Spacer(Modifier.height(70.dp))
                LoginToAccountTextBox()

                Spacer(Modifier.height(20.dp))

                OutlinedTextField(
                    modifier = Modifier.width(300.dp),
                    value = userEmail,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color.White,
                        unfocusedBorderColor = Color.White
                    ),
                    shape = RoundedCornerShape(24.dp),
                    label = { Text(text = "Email", color = Color.White, modifier = Modifier.padding(start = 20.dp)) },
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
                        Text(text = "Password", color = Color.White, modifier = Modifier.padding(start = 20.dp))
                    },
                    trailingIcon = {
                        IconButton(onClick = {
                            passwordVisibility = !passwordVisibility
                        }){
                            Icon(
                                painter = icon,
                                contentDescription = "Visibility icon")
                        }
                    },
                    visualTransformation = if(passwordVisibility) VisualTransformation.None
                    else PasswordVisualTransformation()



                )

                Spacer(Modifier.height(40.dp))

                Button(
                    enabled = userEmail.isNotEmpty() && userPassword.isNotEmpty(),
                    onClick = { viewModel.signInWithEmailAndPassword(userEmail.trim(), userPassword.trim()) },
                    shape = RoundedCornerShape(24.dp),
                    modifier = Modifier
                        .width(300.dp)
                        .alpha(0.4f),
                    content = {
                        Text(text = "Login")
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.White)
                )

//                        val context = LocalContext.current
//                        val token = "612671903128-mit8lhckd4b4vdbs443p7vcf6vv9fk76.apps.googleusercontent.com"
//
//                    OutlinedButton(
//                        border = ButtonDefaults.outlinedBorder.copy(width = 1.dp),
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .height(50.dp),
//                        onClick = {
//                            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                                .requestIdToken(token)
//                                .requestEmail()
//                                .build()
//
//                            val googleSignInClient = GoogleSignIn.getClient(context, gso)
//                            launcher.launch(googleSignInClient.signInIntent)
//                        },
//                        content = {
//                            Row(
//                                modifier = Modifier.fillMaxWidth(),
//                                horizontalArrangement = Arrangement.SpaceBetween,
//                                verticalAlignment = Alignment.CenterVertically,
//                                content = {
//                                    Icon(
//                                        tint = Color.Unspecified,
//                                        painter = painterResource(id = R.drawable.googleg_standard_color_18),
//                                        contentDescription = null,
//                                    )
//                                    Text(
//                                        style = MaterialTheme.typography.button,
//                                        color = MaterialTheme.colors.onSurface,
//                                        text = "Google"
//                                    )
//                                    Icon(
//                                        tint = Color.Transparent,
//                                        imageVector = Icons.Default.MailOutline,
//                                        contentDescription = null,
//                                    )
//                                }
//                            )
//                        }
//                    )

                        when(state.status) {
                            LoadingState.Status.SUCCESS -> {
                                Text(text = "Success")
                            }
                            LoadingState.Status.FAILED -> {
                                Text(text = state.msg ?: "Error")
                            }
                            LoadingState.Status.IDLE -> {
                                Text("")
                            }
                            else -> { }
                        }

                        if (Firebase.auth.currentUser != null){
                            Text(text = Firebase.auth.currentUser?.email.toString())
                        }
                Text(
                    text = "Forgot your password?",
                    fontSize = 16.sp,
                    color = Color.White,
                    fontFamily = FontFamily(Font(R.font.verdana))
                )
                Spacer(Modifier.height(10.dp))
                TextButton(onClick = { /* Do something! */ }) {
                    Text(
                        text = "Click here",
                        fontSize = 16.sp,
                        color = Color.Black,
                        fontFamily = FontFamily(Font(R.font.verdana))
                    )
                }
                Spacer(Modifier.height(10.dp))
                Text(
                    text = "Don't have an account?",
                    fontSize = 16.sp,
                    color = Color.White,
                    fontFamily = FontFamily(Font(R.font.verdana))
                )
                Spacer(Modifier.height(10.dp))
                TextButton(onClick = { /* Do something! */ }) {
                    Text(
                        text = "Sign up",
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
fun LoginScreenPreview() {
    LoginScreen(navController = rememberNavController())
}

@Composable
fun LoginToAccountTextBox() {
    Text(
        text = "Login to your account",
        fontSize = 20.sp,
        color = Color.White,
        fontFamily = FontFamily(Font(R.font.verdana))
    )
}

@Composable
fun LoginScreenBackground() {
    Image(
        painterResource(id = R.mipmap.ic_first_page_background_foreground),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier.fillMaxHeight(),
    )
}