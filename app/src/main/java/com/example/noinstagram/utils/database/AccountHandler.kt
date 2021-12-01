package com.example.noinstagram.utils.database

import com.example.noinstagram.model.UserModel
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class AccountHandler {

    private val database = Firebase.database("https://noinstagram-e6c32-default-rtdb.europe-west1.firebasedatabase.app")
    private val refName: String = "Accounts"

    fun Add(user: UserModel, uid: String) {
        val ref: DatabaseReference = database.getReference(refName).child(uid)
        ref
            .child("email")
            .setValue(user.email)
        ref
            .child("username")
            .setValue(user.displayName)
    }

    fun Get(uid: String): UserModel {
        TODO("Not yet implemented")
    }

    fun Delete(uid: String) {
        TODO("Not yet implemented")
    }

}