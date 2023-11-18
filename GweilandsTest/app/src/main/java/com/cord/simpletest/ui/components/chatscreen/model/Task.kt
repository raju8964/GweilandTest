package com.cord.simpletest.ui.components.chatscreen.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Task")
data class Task(
    @PrimaryKey(autoGenerate = true)
val id:Long,
val message:String
)