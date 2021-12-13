package com.example.noinstagram.model

data class CommentModel(
    var id: String? = "",
    val text: String? = "",
    val User: UserModel? = UserModel(),
    val isLiked: Boolean? = false,
    val likesCount: Int? = null,
    val timeStamp: Long? = null
)
