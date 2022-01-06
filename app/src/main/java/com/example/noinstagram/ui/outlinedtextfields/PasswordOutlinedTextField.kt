package com.example.noinstagram.ui.outlinedtextfields

import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

@Composable
fun PasswordOutlinedTextField() {
    var password by remember { mutableStateOf("") }
    var passwordVisibility by remember { mutableStateOf(false) }
    val icon = if (passwordVisibility)
        painterResource(id = com.example.noinstagram.R.drawable.ic_visibility_on)
    else
        painterResource(id = com.example.noinstagram.R.drawable.ic_visibility_off)



    OutlinedTextField(
        value = password,
        onValueChange = {
            password = it
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        modifier = Modifier.width(300.dp),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Color.White,
            unfocusedBorderColor = Color.White
        ),

        shape = RoundedCornerShape(24.dp),
        label = { Text(text = "Password", color = Color.White) },
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
}
