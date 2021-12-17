package com.example.noinstagram.utils.database

import android.content.ContentValues
import android.net.Uri
import android.util.Log
import com.example.noinstagram.data.PostsRepository
import com.example.noinstagram.data.UsersRepository
import com.example.noinstagram.model.CommentModel
import com.example.noinstagram.model.Post
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.io.File

object PostHandler {

    private val database = Firebase.database("https://noinstagram-e6c32-default-rtdb.europe-west1.firebasedatabase.app")
    private const val refName = "Posts"

    suspend fun createNewPost(uri: Uri, description : String?) {
        val newRef = ref().push()
        val picUrl: String
        StorageHandler.run {
            picUrl = uploadPicture(uri, StorageHandler.Folder.POST)
        }
        val post = Post(
            id = newRef.key,
            image = picUrl,
            description = description,
            user = UsersRepository.getCurrentUser(),
            timeStamp = System.currentTimeMillis()
        )

        setPost(post, newRef)
    }

    fun updatePost(post: Post, comment: CommentModel? = null) {
        if (comment != null) {
            comment.id = post.comments.lastIndex.plus(1).toString()
            post.comments.add(comment)
        }
        ref(post.id)
            .setValue(post)
            .addOnSuccessListener {
                Log.d(ContentValues.TAG, "Successfully updated $post in database")
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

private fun setPost(post: Post, ref: DatabaseReference) {
    ref
        .setValue(post)
        .addOnSuccessListener {
            Log.d(ContentValues.TAG, "Successfully added $post to database")
        }
        .addOnFailureListener {
            Log.d(ContentValues.TAG, "Something went wrong setting post to database", it)
        }
}