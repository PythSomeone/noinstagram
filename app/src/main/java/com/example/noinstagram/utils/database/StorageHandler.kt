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
import kotlinx.coroutines.tasks.await
import java.io.File

object StorageHandler {

    enum class Folder {
        PROFILE,
        POST
    }
    private val storage = Firebase.storage
    private val ref = storage.reference

    // It will return download url for picture
    suspend fun uploadPicture(file: File, folder: Folder): String {
        var urlToFile = ""
        val uri = Uri.fromFile(file)
        val riverRef = ref.child("${folder.name}/${UsersRepository.getCurrentUser()?.id}/${uri.lastPathSegment}")

        riverRef.putFile(uri)
            .addOnSuccessListener {
                Log.d(TAG, "$file was added")
            }.await()
        riverRef.downloadUrl.addOnCompleteListener {
            urlToFile = it.result.toString()
        }.await()
        return urlToFile
    }

    // Use in case that multiple pictures are needed

//    fun clearPreviousUploads(ref: StorageReference) {
//        ref.listAll()
//            .addOnCompleteListener {
//                it.result.items.forEach( action = {
//                    it.delete()
//                    Log.d(TAG, "$it was deleted")
//                })
//            }
//    }

}