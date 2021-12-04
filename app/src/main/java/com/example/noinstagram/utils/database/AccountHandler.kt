package com.example.noinstagram.utils.database

import android.util.Log
import com.example.noinstagram.model.UserModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

class AccountHandler {

    private val database = Firebase.database("https://noinstagram-e6c32-default-rtdb.europe-west1.firebasedatabase.app")
    private val refName: String = "Accounts"

    fun createUser(user: UserModel, uid: String) {
        setUser(user, uid)
        addUserEventListener(uid)
    }

    fun updateUser(user: UserModel, uid: String) {
        setUser(user, uid)
    }

    fun deleteUser(uid: String) {
        var ref = database.getReference(refName).child(uid)
        ref.removeValue()
    }

    private fun setUser(user: UserModel, uid: String) {
        val ref = database.getReference(refName).child(uid)
        ref.setValue(user)
            .addOnSuccessListener {
                Log.d(TAG, "Successfully added $user to database")
            }
            .addOnFailureListener {
                Log.d(TAG, "Something went wrong setting user to database", it)
            }
    }

    private fun addUserEventListener(uid: String) {
        var ref = database.getReference(refName).child(uid)
        val userListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Get UserModel object and use the values to update the UI
                val user = dataSnapshot.getValue<UserModel>()
                TODO("This needs to update UI")
                // ...
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
            }
        }
        ref.addValueEventListener(userListener)
    }

    companion object {
        const val TAG = "AccountHandler"
    }

}