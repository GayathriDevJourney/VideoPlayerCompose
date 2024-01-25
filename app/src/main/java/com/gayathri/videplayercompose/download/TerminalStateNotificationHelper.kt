package com.gayathri.videplayercompose.download

import android.app.Notification
import android.content.Context
import androidx.annotation.OptIn
import androidx.media3.common.util.Log
import androidx.media3.common.util.NotificationUtil
import androidx.media3.common.util.UnstableApi
import androidx.media3.common.util.Util
import androidx.media3.exoplayer.offline.Download
import androidx.media3.exoplayer.offline.DownloadManager
import androidx.media3.exoplayer.offline.DownloadNotificationHelper
import com.gayathri.videplayercompose.R
import java.lang.Exception
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TerminalStateNotificationHelper @Inject constructor(
    val context: Context,
    private val notificationHelper: DownloadNotificationHelper,
    firstNotificationId: Int
) : DownloadManager.Listener {

    private var nextNotificationId: Int = firstNotificationId

    @OptIn(UnstableApi::class)
    override fun onDownloadChanged(
        downloadManager: DownloadManager,
        download: Download,
        finalException: Exception?
    ) {
        var notification: Notification? = null
        super.onDownloadChanged(downloadManager, download, finalException)
        Log.d("download_manager_log", "onDownloadChanged download.state ${download.state}")
        when (download.state) {
            Download.STATE_DOWNLOADING -> {
                notification =
                    notificationHelper.buildDownloadCompletedNotification(
                        context,
                        R.drawable.baseline_downloading_24,
                        /* contentIntent= */ null,
                        Util.fromUtf8Bytes(download.request.data)
                    )
            }

            Download.STATE_COMPLETED -> {
                notification =
                    notificationHelper.buildDownloadCompletedNotification(
                        context,
                        R.drawable.baseline_download_done_24,
                        /* contentIntent= */ null,
                        Util.fromUtf8Bytes(download.request.data)
                    )
            }

            Download.STATE_FAILED -> notification =
                notificationHelper.buildDownloadFailedNotification(
                    context,
                    R.drawable.baseline_file_download_off_24,
                    /* contentIntent= */ null,
                    Util.fromUtf8Bytes(download.request.data)
                )

            else -> Unit
        }
        NotificationUtil.setNotification(context, nextNotificationId++, notification)
    }
}
