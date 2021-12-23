package com.example.noinstagram

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.noinstagram.data.UsersRepository
import com.example.noinstagram.ui.components.AddPostSection

@ExperimentalFoundationApi
@Composable
fun AddPostScreen() {
    val userState = remember {
        UsersRepository
    }
    val user = userState.getCurrentUser()

    Column(modifier = Modifier.fillMaxSize()) {
        Spacer(modifier = Modifier.height(4.dp))
        AddPostSection(user!!)
        Spacer(modifier = Modifier.height(25.dp))
        //refresh
    }
}


@ExperimentalFoundationApi
@Composable
@Preview
fun AddPostPreview() {
    AddPostScreen()
}