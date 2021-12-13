package com.example.noinstagram.data

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.example.noinstagram.model.Post
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.ktx.getValue
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
        updateLike(postId, true)
    }

    suspend fun performLike(postId: String) {
        updateLike(postId, false)
    }

    private suspend fun updateLike(
        postId: String,
        isToggle: Boolean
    ) {
        withContext(Dispatchers.IO) {
            val posts = _posts.value.toMutableList()
            for ((index, value) in posts.withIndex()) {
                if (value.id == postId) {

                    val isLiked = if (isToggle) !value.isLiked!! else true

                    // check if isLiked is same as previous state
                    if (isLiked != value.isLiked) {
                        val likesCount =
                            if (isLiked) value.likesCount!!.plus(1) else value.likesCount!!.minus(1)

                        posts[index] = value.copy(isLiked = isLiked, likesCount = likesCount)
                    }

                    break
                }
            }
            this@PostsRepository._posts.value = posts
        }
    }

}