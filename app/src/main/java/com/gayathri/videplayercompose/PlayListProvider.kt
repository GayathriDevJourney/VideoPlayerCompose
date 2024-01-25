package com.gayathri.videplayercompose

import androidx.media3.exoplayer.source.MediaSource
import com.gayathri.ktor_client.model.Video
import com.gayathri.videplayercompose.data.local.VideoDatabase
import com.gayathri.videplayercompose.data.local.mapToUiModel
import com.gayathri.videplayercompose.media.IMediaSourceProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PlayListProvider @Inject constructor(
    private val videoDatabase: VideoDatabase,
    private val mediaSourceProvider: IMediaSourceProvider
) : IPlayListProvider {

    private lateinit var playlistData: List<Video>

    override suspend fun createPlayList(videoId: Int): MutableList<MediaSource> {
        val playlistMediaSources = mutableListOf<MediaSource>()
        withContext(Dispatchers.IO) {
            val video = videoDatabase.videoDao().getVideo(videoId)
            playlistMediaSources.add(mediaSourceProvider.createMediaSources(video))

            val mediaItems = videoDatabase.videoDao().getVideos().filter {
                it.id > videoId
            }.map {
                playlistMediaSources.add(mediaSourceProvider.createMediaSources(it))
                it.mapToUiModel()
            }
            val prevMediaItems = videoDatabase.videoDao().getVideos().filter {
                it.id < videoId
            }.map {
                playlistMediaSources.add(mediaSourceProvider.createMediaSources(it))
                it.mapToUiModel()
            }
            playlistData = mediaItems.plus(prevMediaItems)
            return@withContext playlistMediaSources
        }
        return playlistMediaSources
    }

    override fun getPlaylistData() = playlistData
}

interface IPlayListProvider {
    suspend fun createPlayList(videoId: Int): MutableList<MediaSource>
    fun getPlaylistData(): List<Video>
}