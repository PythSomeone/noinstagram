package com.example.noinstagram.model

data class UserModel(
    var id: String? = null,
    val email: String? = "",
    val displayName: String? = "",
    val description: String? = "",
    var postLikes: MutableList<String?> = mutableListOf(),
    var following: MutableList<String?> = mutableListOf(),
    var followers: MutableList<String?> = mutableListOf()
)
