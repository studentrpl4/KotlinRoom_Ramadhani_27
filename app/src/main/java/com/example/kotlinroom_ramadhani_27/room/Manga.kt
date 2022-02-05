package com.example.kotlinroom_ramadhani_27.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Manga (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val title: String,
    val desc: String
)