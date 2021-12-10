package com.example.noinstagram.ui.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.noinstagram.data.UsersRepository
import com.example.noinstagram.model.UserModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.ktx.getValue


@Composable
fun FollowingToFollowersSection() {

}

@Composable
fun ListOfFollowing(userState: UsersRepository) {
    var followers: List<UserModel>
    userState.userSnapshots.value.forEach(action = {
        followers = it.getValue<UserModel>()!!
    })
    LazyColumn(Modifier.fillMaxWidth(), contentPadding = PaddingValues(15.dp)) {
        itemsIndexed(followers) { _, follower ->
            FollowingRow(follower)
        }
    }
}

@Composable
fun FollowingRow(followingName: DataSnapshot) {
    Row(Modifier.fillMaxWidth()) {
        Text(
            text = "${
                followingName")
            }
    }

    @Composable
    @Preview
    fun FollowingPreview() {
        FollowingToFollowersSection()
        ListOfFollowing()
    }