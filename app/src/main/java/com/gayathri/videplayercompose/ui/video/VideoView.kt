package com.gayathri.videplayercompose.ui.video

import android.app.Activity
import android.content.pm.ActivityInfo
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.media3.common.util.UnstableApi
import androidx.media3.ui.PlayerView
import com.gayathri.videplayercompose.ui.video.custom.PlayerControllerState
import com.gayathri.videplayercompose.ui.video.custom.VideoLayout
import com.gayathri.videplayercompose.videoplayer.VideoPlayerViewModel

@UnstableApi
@Composable
fun VideoView(viewModel: VideoPlayerViewModel, lifecycle: Lifecycle.Event) {
    var showControls by remember {
        mutableStateOf(false)
    }

    val isPlaying = viewModel.isPlaying.collectAsState()

    val playerDurationDataModel = viewModel.playerDurationDataModel.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .background(color = Color.Yellow)
    ) {
        AndroidView(
            factory = { context ->
                PlayerView(context).also {
                    it.player = viewModel.player
                    (context as? Activity)?.requestedOrientation =
                        ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
//                    it.controllerAutoShow = false
                    it.useController = false // default controller disabled
                }
            }, modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(16 / 9f)
                .clickable {
                    showControls = !showControls
                },
            update = {
                when (lifecycle) {
                    Lifecycle.Event.ON_PAUSE -> {
                        it.onPause()
                        it.player?.pause()
                    }

                    Lifecycle.Event.ON_RESUME -> {
                        println("video_player_log onResume")
                        it.onResume()
                    }

                    else -> Unit
                }
            }
        )
        VideoLayout(
            PlayerControllerState(
                isPlaying = isPlaying.value,
                showControls = showControls
            ),
            action = {
                viewModel.onAction(it)
            },
            playerDurationDataModel = viewModel.playerDurationDataModel.value
        )
    }
}
