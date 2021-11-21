package com.example.noinstagram.ui.outlinedtextfields

import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp

@Composable
fun PasswordOutlinedTextField() {
    var password by remember { mutableStateOf("") }
    OutlinedTextField(
        value = password,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Color.White,
            unfocusedBorderColor = Color.White
        ),
        shape = RoundedCornerShape(24.dp),
        label = { Text(text = "Password", color = Color.White) },
        visualTransformation = PasswordVisualTransformation(),
        onValueChange = {
            password = it
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        modifier = Modifier.width(300.dp)
    )
}
