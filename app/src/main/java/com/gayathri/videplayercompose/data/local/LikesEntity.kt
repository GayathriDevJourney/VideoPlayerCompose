package com.gayathri.videplayercompose.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "likes")
data class LikesEntity(
    @PrimaryKey
    val id: Int = 0
)
