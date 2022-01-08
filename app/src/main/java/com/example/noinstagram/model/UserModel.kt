package com.example.noinstagram.model

data class UserModel(
    var id: String? = null,
    val email: String? = "",
    var displayName: String? = "",
    var description: String? = "",
    var image: String? = "https://firebasestorage.googleapis.com/v0/b/noinstagram-e6c32.appspot.com/o/DEFAULT.png?alt=media&token=3e9659e8-3f5c-4bdc-a338-7b321ec0a441",
    var postLikes: MutableList<String?> = mutableListOf(),
    var following: MutableList<String?> = mutableListOf(),
    var followers: MutableList<String?> = mutableListOf()
)
