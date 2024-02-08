package com.gayathri.videplayercompose.media

import android.content.Context
import android.net.Uri
import androidx.annotation.OptIn
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaItem.AdsConfiguration
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.DataSource
import androidx.media3.datasource.DefaultDataSource
import androidx.media3.datasource.cache.CacheDataSource
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.ima.ImaAdsLoader
import androidx.media3.exoplayer.source.DefaultMediaSourceFactory
import androidx.media3.exoplayer.source.DefaultMediaSourceFactory.AdsLoaderProvider
import androidx.media3.exoplayer.source.MediaSource
import androidx.media3.exoplayer.source.ads.AdsLoader
import androidx.media3.ui.PlayerView
import com.gayathri.download_media_service.VideoDownloadUtil
import com.gayathri.download_media_service.VideoDownloadUtil.getDownloadCache
import javax.inject.Inject

@OptIn(UnstableApi::class)
class MediaSourceFactoryProvider @Inject constructor(
    private val context: Context,
    private val adsLoaderProvider: AdsLoader.Provider
) :
    IMediaSourceFactoryProvider {

    override fun getCacheDataSource(): DataSource.Factory {
        // Create a read-only cache data source factory using the download cache.
        val cacheDataSourceFactory: DataSource.Factory =
            CacheDataSource.Factory()
                .setCache(getDownloadCache(context))
                .setUpstreamDataSourceFactory(VideoDownloadUtil.getHttpDataSourceFactory(context))
                .setCacheWriteDataSinkFactory(null) // Disable writing.

        val player = ExoPlayer.Builder(context)
            .setMediaSourceFactory(
                DefaultMediaSourceFactory(context).setDataSourceFactory(cacheDataSourceFactory)
            ).build()
        return cacheDataSourceFactory
    }

    override fun getMediaSourceFactory(): DataSource.Factory = DefaultDataSource.Factory(context)
    override fun getDefaultMediaSourceFactory(playerView: PlayerView): MediaSource.Factory {
        return DefaultMediaSourceFactory(context).setLocalAdInsertionComponents(
            adsLoaderProvider,
            playerView
        )
    }
}

interface IMediaSourceFactoryProvider {
    fun getCacheDataSource(): DataSource.Factory
    fun getMediaSourceFactory(): DataSource.Factory
    fun getDefaultMediaSourceFactory(playerView: PlayerView): MediaSource.Factory
}