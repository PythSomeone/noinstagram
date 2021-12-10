package com.example.noinstagram.utils.database

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.util.Log
import com.example.noinstagram.data.PostsRepository
import com.example.noinstagram.model.Post
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

object PostHandler {

    private val database = Firebase.database("https://noinstagram-e6c32-default-rtdb.europe-west1.firebasedatabase.app")
    private const val refName = "Posts"

    fun setPost(post: Post) {
        val newRef = ref().push()
        val postWithKey = post
        Log.d(TAG, newRef.key.toString())
        postWithKey.id = newRef.key
        newRef
            .setValue(postWithKey)
            .addOnSuccessListener {
                Log.d(ContentValues.TAG, "Successfully added $postWithKey to database")
            }
            .addOnFailureListener {
                Log.d(ContentValues.TAG, "Something went wrong setting post to database", it)
            }
    }

    fun deletePost(uid: String) {
        ref(uid).removeValue()
    }

    fun postListener() {
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