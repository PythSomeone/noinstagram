package com.example.noinstagram.data

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.example.noinstagram.model.CommentModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.ktx.getValue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object CommentsRepository {

    private val _comments = mutableStateOf<List<CommentModel>>(emptyList())
    val comments: State<List<CommentModel>> = _comments

    fun addComment(snapshot: DataSnapshot) {
        val comments = createArray()
        comments.add(snapshot.getValue<CommentModel>() as CommentModel)
        _comments.value = comments
    }
    fun changeComment(snapshot: DataSnapshot) {
        var comments = createArray()
        comments.forEachIndexed { index, comment ->
            if (comment.id == snapshot.key)
                comments[index] = snapshot.getValue<CommentModel>() as CommentModel
        }
        _comments.value = comments
    }
    fun removeComment(snapshot: DataSnapshot) {
        var comments = createArray()
        comments.forEachIndexed { index, comment ->
            if (comment.id == snapshot.key)
                comments.removeAt(index)
        }
        _comments.value = comments
    }

    private fun createArray(): ArrayList<CommentModel> {
        val comments = ArrayList<CommentModel>()
        _comments.value.forEach(action = {
            comments.add(it)
        })
        return comments
    }

    suspend fun toggleLike(commentId: String) {
        updateLike(commentId, true)
    }

    suspend fun performLike(commentId: String) {
        updateLike(commentId, false)
    }

    private suspend fun updateLike(
        commentId: String,
        isToggle: Boolean
    ) {
        withContext(Dispatchers.IO) {
            val comments = _comments.value.toMutableList()
            for ((index, value) in comments.withIndex()) {
                if (value.id == commentId) {

                    val isLiked = if (isToggle) !value.isLiked!! else true

                    // check if isLiked is same as previous state
                    if (isLiked != value.isLiked) {
                        val likesCount =
                            if (isLiked) value.likesCount!!.plus(1) else value.likesCount!!.minus(1)

                        comments[index] = value.copy(isLiked = isLiked, likesCount = likesCount)
                    }

                    break
                }
            }
            this@CommentsRepository._comments.value = comments
        }
    }

}