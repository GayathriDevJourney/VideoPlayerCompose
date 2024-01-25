package com.gayathri.videplayercompose.media

import android.content.Context
import androidx.annotation.OptIn
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.DataSource
import androidx.media3.datasource.DefaultDataSource
import androidx.media3.datasource.cache.CacheDataSource
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.source.DefaultMediaSourceFactory
import com.gayathri.videplayercompose.download.VideoDownloadUtil
import com.gayathri.videplayercompose.download.VideoDownloadUtil.getDownloadCache
import javax.inject.Inject

@OptIn(UnstableApi::class)
class MediaSourceFactoryProvider @Inject constructor(private val context: Context) :
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
}

interface IMediaSourceFactoryProvider {
    fun getCacheDataSource(): DataSource.Factory
    fun getMediaSourceFactory(): DataSource.Factory
}