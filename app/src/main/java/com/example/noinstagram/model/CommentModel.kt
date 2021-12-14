package com.example.noinstagram.model

data class CommentModel(
    var id: String? = null,
    val text: String? = "",
    val user: UserModel? = UserModel(),
    val timeStamp: Long? = null
)
