package com.example.noinstagram.utils.database

import android.content.ContentValues.TAG
import android.net.Uri
import android.util.Log
import com.example.noinstagram.data.UsersRepository
import com.example.noinstagram.model.Post
import com.example.noinstagram.model.UserModel
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import java.io.File

object StorageHandler {

    private val storage = Firebase.storage
    private val ref = storage.reference

    fun uploadProfilePicture(file: File, user: UserModel) {
        var uri = Uri.fromFile(file)
        var riverRef = ref.child("Profile pictures/${user.id}")

        riverRef.putFile(uri)
            .addOnSuccessListener {
                riverRef.downloadUrl.addOnCompleteListener {
                    user.photo = it.result.toString()
                    UsersRepository.users.value.forEach( action = {
                        if (it.id == user.id)
                            UserHandler.setUser(user, user.id!!)
                    })
                }
                Log.d(TAG, "$file was added")
            }
    }

    fun uploadPostPicture(file: File, post: Post) {
        var uri = Uri.fromFile(file)
        var riverRef = ref.child("Post pictures/${post.id}")

        riverRef.putFile(uri)
            .addOnSuccessListener {
                riverRef.downloadUrl.addOnCompleteListener {
                    post.image = it.result.toString()
                    UsersRepository.users.value.forEach( action = {
                        if (it.id == post.id)
                            PostHandler.updatePost(post)
                    })
                }
                Log.d(TAG, "$file was added")
            }
    }

    fun clearPreviousUploads(ref: StorageReference) {
        ref.listAll()
            .addOnCompleteListener {
                it.result.items.forEach( action = {
                    it.delete()
                    Log.d(TAG, "$it was deleted")
                })
            }
    }

}