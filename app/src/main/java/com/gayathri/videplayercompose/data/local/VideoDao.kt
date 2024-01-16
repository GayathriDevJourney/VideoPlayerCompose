package com.gayathri.videplayercompose.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface VideoDao {

    @Query("SELECT * FROM videos")
    suspend fun getVideos(): List<VideoEntity>

    @Query("SELECT * FROM videos WHERE id = :videoId")
    suspend fun getVideo(videoId: Int): VideoEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(video: VideoEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(videos: List<VideoEntity>)

    @Query("DELETE FROM videos")
    suspend fun clear()
}