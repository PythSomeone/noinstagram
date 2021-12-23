package com.example.noinstagram.model

data class UserModel(
    var id: String? = null,
    val email: String? = "",
    var displayName: String? = "",
    var description: String? = "",
    var image: String? = "https://firebasestorage.googleapis.com/v0/b/noinstagram-e6c32.appspot.com/o/DEFAULT.png?alt=media&token=5909137c-8e0c-48d1-aa4d-6061cb0b6132",
    var postLikes: MutableList<String?> = mutableListOf(),
    var following: MutableList<String?> = mutableListOf(),
    var followers: MutableList<String?> = mutableListOf()
)
