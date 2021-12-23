package com.example.noinstagram

import android.graphics.Bitmap
import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.noinstagram.data.UsersRepository
import com.example.noinstagram.model.UserModel
import com.example.noinstagram.ui.components.EditProfileSection
import com.example.noinstagram.ui.components.PostSection
import com.example.noinstagram.ui.components.ProfileSection
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.google.firebase.database.ktx.getValue
import kotlinx.coroutines.delay

@ExperimentalFoundationApi
@Composable
fun EditProfileScreen() {
    val userState = remember {
        UsersRepository
    }
    var user = UserModel()
    var refreshing by remember { mutableStateOf(false) }
    userState.users.value.forEach(action = {
        if (it.id == "uxjq0kpK8Ihdl4A4Ow2QCjbhTWz1") {
            user = it
        }
    })

    Log.d("TAG", "$user")
    Column(modifier = Modifier.fillMaxSize()) {
        Spacer(modifier = Modifier.height(4.dp))
        EditProfileSection(user)
        Spacer(modifier = Modifier.height(25.dp))
        //refresh
        LaunchedEffect(refreshing) {
            if (refreshing) {
                delay(2000)
                refreshing = false
            }
        }
    }
}

@ExperimentalFoundationApi
@Composable
@Preview
fun EditProfilePreview() {
    EditProfileScreen()
}

