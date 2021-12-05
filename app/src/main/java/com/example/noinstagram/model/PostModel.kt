package com.example.noinstagram.model

data class Post(
    val id: Int,
    val image: String,
    val user: UserModel,
    val isLiked: Boolean = false,
    val likesCount: Int,
    val commentsCount: Int,
    val timeStamp: Long
)
