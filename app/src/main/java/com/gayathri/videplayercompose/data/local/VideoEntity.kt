package com.gayathri.videplayercompose.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.gayathri.ktor_client.model.Video

@Entity(tableName = "videos")
data class VideoEntity(
    @PrimaryKey
    val id: Int,
    @ColumnInfo
    val description: String,
    @ColumnInfo
    val source: String,
    @ColumnInfo
    val subtitle: String,
    @ColumnInfo
    val thumb: String,
    @ColumnInfo
    val title: String
)

fun List<VideoEntity>.mapToUiModelList() = map {
    Video(it.id, it.description, it.source, it.subtitle, it.thumb, it.title)
}


fun List<Video>.mapToEntity() = map {
    VideoEntity(it.id, it.description, it.source, it.subtitle, it.thumb, it.title)
}

fun Video.mapToEntity() =
    VideoEntity(this.id, this.description, this.source, this.subtitle, this.thumb, this.title)

fun VideoEntity.mapToUiModel() =
    Video(this.id, this.description, this.source, this.subtitle, this.thumb, this.title)
