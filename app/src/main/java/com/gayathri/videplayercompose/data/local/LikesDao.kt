package com.gayathri.videplayercompose.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface LikesDao {

    @Query("SELECT * FROM likes")
    suspend fun getMyLikes(): List<LikesEntity>

    @Query("SELECT * FROM likes WHERE id = :videoId")
    suspend fun getLikeStatus(videoId: Int): LikesEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(like: LikesEntity)

    @Query("DELETE FROM likes WHERE id = :videoId")
    suspend fun delete(videoId: Int)


    @Query("DELETE FROM likes")
    suspend fun clear()
}