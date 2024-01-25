package com.gayathri.videplayercompose.media

import androidx.annotation.OptIn
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.source.ProgressiveMediaSource
import com.gayathri.ktor_client.AppConstant
import com.gayathri.videplayercompose.data.local.VideoEntity
import com.gayathri.videplayercompose.data.local.mapToUiModel
import javax.inject.Inject

class MediaSourceProvider @Inject constructor(
    private val mediaSourceFactoryProvider: IMediaSourceFactoryProvider,
) : IMediaSourceProvider {
    @OptIn(UnstableApi::class)
    override fun createMediaSources(video: VideoEntity): ProgressiveMediaSource {
        return ProgressiveMediaSource.Factory(mediaSourceFactoryProvider.getMediaSourceFactory())
            .createMediaSource(createMediaItem(video))
    }

    private fun createMediaItem(video: VideoEntity): MediaItem {
        // Build a media item with a media ID.
        with(video.mapToUiModel()) {
            val uri = AppConstant.MEDIA_BASE_URL.plus(source)
            return MediaItem.Builder().setUri(uri).setMediaId(id.toString())
                .setTag(video.mapToUiModel()).build()
        }
    }
}

interface IMediaSourceProvider {
    fun createMediaSources(video: VideoEntity): ProgressiveMediaSource
}
