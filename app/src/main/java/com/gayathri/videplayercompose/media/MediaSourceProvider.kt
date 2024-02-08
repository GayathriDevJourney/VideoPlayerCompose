package com.gayathri.videplayercompose.media

import android.net.Uri
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
        return ProgressiveMediaSource.Factory(mediaSourceFactoryProvider.getCacheDataSource())
            .createMediaSource(createMediaItem(video))
    }

    private fun createMediaItem(video: VideoEntity): MediaItem {
        // Build a media item with a media ID.
        with(video.mapToUiModel()) {
            val uri = AppConstant.MEDIA_BASE_URL.plus(source)
            return MediaItem.Builder().setUri(uri).setMediaId(id.toString())
                .setAdsConfiguration(
                    MediaItem.AdsConfiguration.Builder(Uri.parse(SAMPLE_VAST_TAG_URL)).build()
                )
                .setTag(video.mapToUiModel()).build()
        }
    }

    companion object {
        const val SAMPLE_VAST_TAG_URL =
            "https://pubads.g.doubleclick.net/gampad/ads?iu=/21775744923/external/single_preroll_skippable&sz=640x480&ciu_szs=300x250%2C728x90&gdfp_req=1&output=vast&unviewed_position_start=1&env=vp&impl=s&correlator=";
    }
}

interface IMediaSourceProvider {
    fun createMediaSources(video: VideoEntity): ProgressiveMediaSource
}
