package com.gayathri.videplayercompose.download

import android.content.Context
import androidx.annotation.Nullable
import androidx.annotation.OptIn
import androidx.media3.common.util.UnstableApi
import androidx.media3.database.DatabaseProvider
import androidx.media3.database.StandaloneDatabaseProvider
import androidx.media3.datasource.DataSource
import androidx.media3.datasource.DefaultDataSource
import androidx.media3.datasource.DefaultHttpDataSource
import androidx.media3.datasource.cache.Cache
import androidx.media3.datasource.cache.CacheDataSource
import androidx.media3.datasource.cache.NoOpCacheEvictor
import androidx.media3.datasource.cache.SimpleCache
import androidx.media3.datasource.cronet.CronetDataSource
import androidx.media3.datasource.cronet.CronetUtil
import androidx.media3.exoplayer.offline.DownloadManager
import androidx.media3.exoplayer.offline.DownloadNotificationHelper
import java.io.File
import java.net.CookieHandler
import java.net.CookieManager
import java.net.CookiePolicy
import java.util.concurrent.Executors

@OptIn(UnstableApi::class)
object VideoDownloadUtil {
    const val DOWNLOAD_NOTIFICATION_CHANNEL_ID = "download_channel";
    const val USE_CRONET_FOR_NETWORKING = true;
    const val TAG = "DemoUtil";
    private const val DOWNLOAD_CONTENT_DIRECTORY = "downloads";
    private lateinit var downloadManager: DownloadManager
    private lateinit var databaseProvider: DatabaseProvider
    private lateinit var downloadCache: Cache
    private var downloadDirectory: File? = null
    lateinit var downloadTracker: DownloadTracker
    private lateinit var dataSourceFactory: DataSource.Factory
    private lateinit var downloadNotificationHelper: DownloadNotificationHelper
    private lateinit var httpDataSourceFactory: DataSource.Factory

    fun getDownloadManager(context: Context): DownloadManager {
        ensureDownloadManagerInitialized(context);
        return downloadManager
    }

    private fun getDataSourceFactory(context: Context): DataSource.Factory {
        if (!::dataSourceFactory.isInitialized) {
            val upstreamFactory =
                DefaultDataSource.Factory(context, getHttpDataSourceFactory(context))
            dataSourceFactory =
                buildReadOnlyCacheDataSource(upstreamFactory, getDownloadCache(context));
        }
        return dataSourceFactory
    }

    fun getHttpDataSourceFactory(context: Context): DataSource.Factory {
        var context = context
        if (!::httpDataSourceFactory.isInitialized) {
            if (USE_CRONET_FOR_NETWORKING) {
                context = context.applicationContext
                @Nullable val cronetEngine = CronetUtil.buildCronetEngine(context)
                if (cronetEngine != null) {
                    httpDataSourceFactory =
                        CronetDataSource.Factory(cronetEngine, Executors.newSingleThreadExecutor())
                }
            }
            if (!::httpDataSourceFactory.isInitialized) {
                // We don't want to use Cronet, or we failed to instantiate a CronetEngine.
                val cookieManager = CookieManager()
                cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ORIGINAL_SERVER)
                CookieHandler.setDefault(cookieManager)
                httpDataSourceFactory = DefaultHttpDataSource.Factory()
            }
        }
        return httpDataSourceFactory
    }


    @OptIn(UnstableApi::class)
    private fun buildReadOnlyCacheDataSource(
        upstreamFactory: DefaultDataSource.Factory,
        downloadCache: Cache
    ): DataSource.Factory {
        return CacheDataSource.Factory()
            .setCache(downloadCache)
            .setUpstreamDataSourceFactory(upstreamFactory)
            .setCacheWriteDataSinkFactory(null)
            .setFlags(CacheDataSource.FLAG_IGNORE_CACHE_ON_ERROR);
    }

    @OptIn(UnstableApi::class)
    private fun ensureDownloadManagerInitialized(context: Context): DownloadManager {
        if (!::downloadManager.isInitialized) {
            downloadManager =
                DownloadManager(
                    context,
                    getDatabaseProvider(context),
                    getDownloadCache(context),
                    getDataSourceFactory(context),
                    Executors.newFixedThreadPool(6)
                )
        }
        return downloadManager
    }

    @OptIn(UnstableApi::class)
    private fun getDatabaseProvider(context: Context): DatabaseProvider {
        if (!::databaseProvider.isInitialized) {
            databaseProvider = StandaloneDatabaseProvider(context)
        }
        return databaseProvider
    }

    @OptIn(UnstableApi::class)
    fun getDownloadCache(context: Context): Cache {
        if (!::downloadCache.isInitialized) {
            val downloadContentDirectory =
                File(getDownloadDirectory(context), DOWNLOAD_CONTENT_DIRECTORY);
            downloadCache = SimpleCache(
                downloadContentDirectory, NoOpCacheEvictor(),
                getDatabaseProvider(context)
            )
        }
        return downloadCache
    }

    private fun getDownloadDirectory(context: Context): File? {
        if (downloadDirectory == null) {
            downloadDirectory = context.getExternalFilesDir(null)
            if (downloadDirectory == null) {
                downloadDirectory = context.filesDir
            }
        }
        return downloadDirectory
    }

    fun getDownloadNotificationHelper(
        context: Context?
    ): DownloadNotificationHelper {
        if (!::downloadNotificationHelper.isInitialized) {
            downloadNotificationHelper = DownloadNotificationHelper(
                context!!, DOWNLOAD_NOTIFICATION_CHANNEL_ID
            )
        }
        return downloadNotificationHelper
    }
}
