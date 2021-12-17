package com.example.noinstagram.model

data class UserModel(
    var id: String? = null,
    val email: String? = "",
    var displayName: String? = "",
    var description: String? = "",
    var following: MutableList<String?> = mutableListOf(),
    var followers: MutableList<String?> = mutableListOf()
)
