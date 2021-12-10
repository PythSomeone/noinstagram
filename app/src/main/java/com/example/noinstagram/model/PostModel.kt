package com.example.noinstagram.model

data class Post(
    var id: String? = null,
    val image: String? = null,
    val user: UserModel? = null,
    val isLiked: Boolean? = false,
    val likesCount: Int? = null,
    val commentsCount: Int? = null,
    val timeStamp: Long? = null
)