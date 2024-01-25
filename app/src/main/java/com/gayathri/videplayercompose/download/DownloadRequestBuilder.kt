package com.gayathri.videplayercompose.download

import android.content.Context
import android.net.Uri
import androidx.annotation.OptIn
import androidx.media3.common.util.Log
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.offline.DownloadRequest
import androidx.media3.exoplayer.offline.DownloadService
import com.gayathri.ktor_client.AppConstant
import com.gayathri.ktor_client.model.Video
import javax.inject.Inject

class DownloadRequestBuilder @Inject constructor(val context: Context) {

    @OptIn(UnstableApi::class)
    fun downloadMedia(video: Video) {
        Log.d("download_manager_log", "downloadMedia ${video.source}")
        val downloadRequest = DownloadRequest.Builder(
            video.id.toString(),
            Uri.parse((AppConstant.MEDIA_BASE_URL.plus(video.source)))
        ).build()
        DownloadService.sendAddDownload(
            context,
            VideoDownloadService::class.java,
            downloadRequest,
            /* foreground= */ false
        )
    }
}
