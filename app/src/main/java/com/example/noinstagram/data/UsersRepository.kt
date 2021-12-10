package com.example.noinstagram.data

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import com.example.noinstagram.model.UserModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

object UsersRepository {

    private val _userSnapshots = mutableStateOf<List<DataSnapshot>>(emptyList())
    val userSnapshots: State<List<DataSnapshot>> = _userSnapshots

    fun addSnapshot(snapshot: DataSnapshot) {
        val userSnapshots = createArray()
        userSnapshots.add(snapshot)
        _userSnapshots.value = userSnapshots
    }
    fun changeSnapshot(snapshot: DataSnapshot) {
        var userSnapshots = createArray()
        userSnapshots.forEachIndexed { index, dataSnapshot ->
            if (dataSnapshot.key == snapshot.key)
                userSnapshots[index] = snapshot
        }
        _userSnapshots.value = userSnapshots
    }
    fun removeSnapshot(snapshot: DataSnapshot) {
        var userSnapshots = createArray()
        userSnapshots.forEachIndexed { index, dataSnapshot ->
            if (dataSnapshot.key == snapshot.key)
                userSnapshots.removeAt(index)
        }
        _userSnapshots.value = userSnapshots
    }

    fun getCurrentUser(): UserModel {
        val currentUserUID = Firebase.auth.currentUser?.uid
        if (currentUserUID != null) {
            userSnapshots.value.forEach(action = {
                if (it.key == currentUserUID)
                    return it.getValue<UserModel>() as UserModel
            })
        }
        return UserModel()
    }

    private fun createArray(): ArrayList<DataSnapshot> {
        val userSnapshots = ArrayList<DataSnapshot>()
        _userSnapshots.value.forEach(action = {
            userSnapshots.add(it)
        })
        return userSnapshots
    }

}