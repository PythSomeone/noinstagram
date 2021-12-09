package com.example.noinstagram.data

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.google.firebase.database.DataSnapshot

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

    private fun createArray(): ArrayList<DataSnapshot> {
        val userSnapshots = ArrayList<DataSnapshot>()
        _userSnapshots.value.forEach(action = {
            userSnapshots.add(it)
        })
        return userSnapshots
    }

}