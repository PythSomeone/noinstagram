package com.example.noinstagram.utils.database

import android.content.ContentValues.TAG
import android.util.Log
import com.example.noinstagram.data.UsersRepository
import com.example.noinstagram.model.Post
import com.example.noinstagram.model.UserModel
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import java.io.File


object UserHandler {

    private val database = Firebase.database("https://noinstagram-e6c32-default-rtdb.europe-west1.firebasedatabase.app")
    private const val refName = "Users"

    fun setUser(user: UserModel) {
        ref(user.id).setValue(user)
            .addOnSuccessListener {
                Log.d(TAG, "Successfully added $user to database")
            }
            .addOnFailureListener {
                Log.d(TAG, "Something went wrong setting user to database", it)
            }
    }

    fun deleteUser(uid: String) {
        ref(uid).removeValue()
    }

    fun userListener() {
        ref().addChildEventListener(object: ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                UsersRepository.addUser(snapshot)
                Log.d(TAG, "Children $snapshot was added")
            }
            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                UsersRepository.changeUser(snapshot)
                Log.d(TAG, "Children $snapshot was changed")
            }
            override fun onChildRemoved(snapshot: DataSnapshot) {
                UsersRepository.removeUser(snapshot)
                Log.d(TAG, "Children $snapshot was removed")
            }
            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}
            override fun onCancelled(error: DatabaseError) {}
        })
    }

    private fun ref(uid: String? = null): DatabaseReference {
        return if (uid == null)
            database.getReference(refName)
        else
            database.getReference(refName).child(uid)
    }

}

