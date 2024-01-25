package com.gayathri.videplayercompose.download

import android.app.Notification
import androidx.media3.common.util.Log
import androidx.media3.common.util.UnstableApi
import androidx.media3.common.util.Util
import androidx.media3.exoplayer.offline.Download
import androidx.media3.exoplayer.offline.DownloadManager
import androidx.media3.exoplayer.offline.DownloadService
import androidx.media3.exoplayer.scheduler.PlatformScheduler
import androidx.media3.exoplayer.scheduler.Scheduler
import com.gayathri.videplayercompose.R


private const val JOB_ID = 1
private const val FOREGROUND_NOTIFICATION_ID = 1
private const val DOWNLOAD_NOTIFICATION_CHANNEL_ID = "download_channel";

@UnstableApi
class VideoDownloadService : DownloadService(
    FOREGROUND_NOTIFICATION_ID,
    DEFAULT_FOREGROUND_NOTIFICATION_UPDATE_INTERVAL,
    DOWNLOAD_NOTIFICATION_CHANNEL_ID,
    R.string.exo_download_notification_channel_name, 0
) {
    override fun getDownloadManager(): DownloadManager {
        val downloadManager = VideoDownloadUtil.getDownloadManager(this)
        val downloadNotificationHelper = VideoDownloadUtil.getDownloadNotificationHelper(this)
        downloadManager.addListener(
            TerminalStateNotificationHelper(
                this, downloadNotificationHelper, FOREGROUND_NOTIFICATION_ID + 1
            )
        )
        Log.d(
            "download_manager_log",
            "getDownloadManager getDownloads ${downloadManager.currentDownloads}"
        )
        return downloadManager
    }

    override fun getScheduler(): Scheduler? {
        return if (Util.SDK_INT >= 21) PlatformScheduler(this, JOB_ID) else null
    }

    override fun getForegroundNotification(
        downloads: MutableList<Download>,
        notMetRequirements: Int
    ): Notification {
        return VideoDownloadUtil.getDownloadNotificationHelper(/* context= */ this)
            .buildProgressNotification(
                /* context= */ this,
                R.drawable.baseline_download_done_24,
                /* contentIntent= */ null,
                /* message= */ null,
                downloads,
                notMetRequirements
            );
    }
}
