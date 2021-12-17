package com.example.noinstagram.model

import android.net.Uri
import com.google.android.gms.tasks.Task

data class UserModel(
    var id: String? = null,
    val email: String? = "",
    val displayName: String? = "",
    val description: String? = "",
    var photo: String? = "",
    var postLikes: MutableList<String?> = mutableListOf(),
    var following: MutableList<String?> = mutableListOf(),
    var followers: MutableList<String?> = mutableListOf()
)
