package com.example.noinstagram.data

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.example.noinstagram.model.UserModel
import com.example.noinstagram.utils.database.UserHandler
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

object UsersRepository {

    private val _users = mutableStateOf<List<UserModel>>(emptyList())
    val users: State<List<UserModel>> = _users

    fun addUser(snapshot: DataSnapshot) {
        val users = listToArray()
        if (snapshot.getValue<UserModel>()?.id == null) {
            val newValue = snapshot.getValue<UserModel>()
            newValue?.id = snapshot.key
            users.add(newValue!!)
        } else
            users.add(snapshot.getValue<UserModel>() as UserModel)
        _users.value = users
    }

    fun changeUser(snapshot: DataSnapshot) {
        var users = listToArray()
        users.forEachIndexed { index, user ->
            if (user.id == snapshot.key)
                users[index] = snapshot.getValue<UserModel>() as UserModel
        }
        _users.value = users
    }

    fun removeUser(snapshot: DataSnapshot) {
        var users = listToArray()
        users.forEachIndexed { index, user ->
            if (user.id == snapshot.key)
                users.removeAt(index)
        }
        _users.value = users
    }

    private fun listToArray(): ArrayList<UserModel> {
        val userSnapshots = ArrayList<UserModel>()
        _users.value.forEach(action = {
            userSnapshots.add(it)
        })
        return userSnapshots
    }

    fun toggleFollow(
        followerUid: String,
        followedUid: String
    ) {
        var follower = getUser(followerUid)
        var followed = getUser(followedUid)

        if (follower != null && followed != null) {
            if (!userIsFollowed(follower.id!!, followed.id!!)) {
                follower.following.add(followed.id)
                followed.followers.add(follower.id)
            } else {
                follower.following.remove(followed.id)
                followed.followers.remove(follower.id)
            }
            // Send snapshots to database
            UserHandler.setUser(follower)
            UserHandler.setUser(followed)
        }
    }

    fun getFollowers(uid: String): MutableList<UserModel> {
        val followers: MutableList<UserModel> = mutableListOf()
        getUser(uid)?.followers?.forEach(action = {
            if (it != null) {
                val follower = getUser(it)!!
                if (follower != null) {
                    followers.add(follower)
                }
            }
        })
        return followers
    }

    fun getFollowing(uid: String): MutableList<UserModel> {
        val followers: MutableList<UserModel> = mutableListOf()
        getUser(uid)?.following?.forEach(action = {
            if (it != null) {
                val follower = getUser(it)!!
                if (follower != null) {
                    followers.add(follower)
                }
            }
        })
        return followers
    }

    fun userIsFollowed(followerUid: String, followedUid: String): Boolean {
        getUser(followerUid)?.following?.forEach(action = {
            if (it == followedUid) return true
        })
        return false
    }

    fun getUser(uid: String): UserModel? {
        users.value.forEach(action = {
            if (it.id == uid)
                return it
        })
        return null
    }

    fun getCurrentUser(): UserModel? {
        val uid = Firebase.auth.currentUser?.uid
        return if (uid != null) {
            getUser(uid)
        } else
            null
    }

}