package com.example.noinstagram.data

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.example.noinstagram.model.Post
import com.example.noinstagram.utils.database.PostHandler
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object PostsRepository {

    private val _posts = mutableStateOf<List<Post>>(emptyList())
    val posts: State<List<Post>> = _posts

    fun addPost(snapshot: DataSnapshot) {
        val posts = createArray()
        posts.add(snapshot.getValue<Post>() as Post)
        _posts.value = posts
    }

    fun changePost(snapshot: DataSnapshot) {
        var posts = createArray()
        posts.forEachIndexed { index, post ->
            if (post.id == snapshot.key)
                posts[index] = snapshot.getValue<Post>() as Post
        }
        _posts.value = posts
    }

    fun removePost(snapshot: DataSnapshot) {
        var posts = createArray()
        posts.forEachIndexed { index, post ->
            if (post.id == snapshot.key)
                posts.removeAt(index)
        }
        _posts.value = posts
    }

    private fun createArray(): ArrayList<Post> {
        val posts = ArrayList<Post>()
        _posts.value.forEach(action = {
            posts.add(it)
        })
        return posts
    }

    suspend fun toggleLike(postId: String) {
        updateLike(postId)
    }

    private suspend fun updateLike(
        postId: String
    ) {
        withContext(Dispatchers.IO) {
            val posts = _posts.value.toMutableList()
            for ((index, value) in posts.withIndex()) {
                if (value.id == postId) {
                    if (postIsLiked(postId).not()) {
                        posts[index].userLikes.add(Firebase.auth.currentUser?.uid!!)
                        Log.d("TAG", "Added ${posts[index]}")
                    } else {
                        posts[index].userLikes.remove(Firebase.auth.currentUser?.uid!!)
                        Log.d("TAG", "Deleted ${posts[index]}")
                    }
                    PostHandler.updatePost(posts[index])
                    break
                }
            }
            this@PostsRepository._posts.value = posts
        }
    }

    fun getPost(postId: String): Post? {
        posts.value.forEach { post ->
            if (post.id == postId)
                return post
        }
        return null
    }

    fun getPostsForUser(uid: String): List<Post> {
        val userPosts = ArrayList<Post>()
        posts.value.forEach(action = {
            if (it.id == uid)
                userPosts.add(it)
        })
        return userPosts
    }

}

fun postIsLiked(postId: String): Boolean {
    return PostsRepository.getPost(postId)?.userLikes?.contains(Firebase.auth.currentUser?.uid)!!
}