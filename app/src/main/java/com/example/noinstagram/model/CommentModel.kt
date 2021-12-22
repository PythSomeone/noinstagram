package com.example.noinstagram.model

data class CommentModel(
    var id: String? = null,
    val text: String? = "",
    val user: UserModel? = null,
    val timeStamp: Long? = 0
)
