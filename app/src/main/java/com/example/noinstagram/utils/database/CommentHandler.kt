package com.example.noinstagram.utils.database

import android.content.ContentValues
import android.util.Log
import com.example.noinstagram.data.PostsRepository
import com.example.noinstagram.model.CommentModel
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

object CommentHandler {

    private val database = Firebase.database("https://noinstagram-e6c32-default-rtdb.europe-west1.firebasedatabase.app")
    private const val refName = "Comments"

    fun setComment(comment: CommentModel) {
        val newRef = ref().push()
        val commentWithKey = comment
        Log.d(ContentValues.TAG, newRef.key.toString())
        commentWithKey.id = newRef.key
        newRef
            .setValue(commentWithKey)
            .addOnSuccessListener {
                Log.d(ContentValues.TAG, "Successfully added $commentWithKey to database")
            }
            .addOnFailureListener {
                Log.d(ContentValues.TAG, "Something went wrong setting comment to database", it)
            }
    }

    fun deleteComment(uid: String) {
        ref(uid).removeValue()
    }

    fun commentListener() {
        ref().addChildEventListener(object: ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                PostsRepository.addPost(snapshot)
                Log.d(ContentValues.TAG, "Children $snapshot was added")
            }
            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                PostsRepository.changePost(snapshot)
                Log.d(ContentValues.TAG, "Children $snapshot was changed")
            }
            override fun onChildRemoved(snapshot: DataSnapshot) {
                PostsRepository.removePost(snapshot)
                Log.d(ContentValues.TAG, "Children $snapshot was removed")
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