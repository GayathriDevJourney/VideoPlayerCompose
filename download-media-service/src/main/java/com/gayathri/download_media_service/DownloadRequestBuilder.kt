package com.gayathri.download_media_service

import android.content.Context
import android.net.Uri
import androidx.annotation.OptIn
import androidx.media3.common.util.Log
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.offline.DownloadRequest
import androidx.media3.exoplayer.offline.DownloadService
import com.gayathri.download_media_service.model.DownloadVideo
import javax.inject.Inject

class DownloadRequestBuilder @Inject constructor(private val context: Context) {

    @OptIn(UnstableApi::class)
    fun downloadMedia(video: DownloadVideo) {
        Log.d("download_manager_log", "downloadMedia $video")
        val downloadRequest = DownloadRequest.Builder(
            video.id,
            Uri.parse((video.url))
        ).build()
        DownloadService.sendAddDownload(
            context,
            VideoDownloadService::class.java,
            downloadRequest,
            /* foreground= */ false
        )
    }
}
