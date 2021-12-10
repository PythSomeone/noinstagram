package com.example.noinstagram.model

data class UserModel(
    val email: String? = "",
    val displayName: String? = "",
    val description: String? = "",
    val postLikes: List<String?> = emptyList()
)
