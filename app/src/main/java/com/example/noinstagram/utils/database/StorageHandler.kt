package com.example.noinstagram.utils.database

import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

object StorageHandler {

    private val storage = Firebase.storage
    private val ref = storage.reference

}