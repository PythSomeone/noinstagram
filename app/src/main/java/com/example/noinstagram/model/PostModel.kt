package com.example.noinstagram.model

data class Post(
    var id: String? = null,
    val image: String? = null,
    val user: UserModel? = null,
    val comments: MutableList<CommentModel> = mutableListOf(),
    val userLikes: MutableList<String> = mutableListOf(),
    val timeStamp: Long? = null
)