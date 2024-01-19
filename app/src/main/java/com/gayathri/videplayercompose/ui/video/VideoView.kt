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
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.media3.common.util.Log
import androidx.media3.common.util.UnstableApi
import androidx.media3.ui.PlayerView
import com.gayathri.videplayercompose.home.ObserveLifecycleEvents
import com.gayathri.videplayercompose.ui.video.custom.DurationController
import com.gayathri.videplayercompose.ui.video.custom.VideoLayout
import com.gayathri.videplayercompose.ui.video.custom.VideoPlayerControlAction
import com.gayathri.videplayercompose.ui.video.custom.state.PlayerControllerState
import com.gayathri.videplayercompose.ui.video.custom.state.PlayerOrientation
import com.gayathri.videplayercompose.videoplayer.VideoPlayerViewModel

@UnstableApi
@Composable
fun VideoView(viewModel: VideoPlayerViewModel) {
    val activity = LocalContext.current

    var showControls by remember { mutableStateOf(true) }

    val orientation = viewModel.orientation.collectAsState()

    val playerState = viewModel.playerState.collectAsState()

    val isPlaying = viewModel.isPlaying.collectAsState()

    val playerDurationDataModel = viewModel.playerDurationDataModel.collectAsState()

    val screenOrientation = if (orientation.value == PlayerOrientation.Portrait) {
        ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    } else {
        ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
    }
    (activity as? Activity)?.requestedOrientation = screenOrientation
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
                    it.useController = false // default controller disabled
                }
            }, modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(16 / 9f)
                .clickable {
                    showControls = !showControls
                },
            update = {
                Log.d("video_player_log", "exo player update ${it.player}")
            }
        )
        VideoLayout(
            PlayerControllerState(
                isPlaying = isPlaying.value,
                playerState = playerState.value,
                showControls = showControls
            ),
            action = {
                viewModel.onAction(it)
            }
        )
        DurationController(
            Modifier.align(Alignment.BottomStart),
            playerDurationDataModel.value,
            onSeekChanged = {
                viewModel.onAction(VideoPlayerControlAction.OnSeekChanged(it))
            },
            onExpand = {
                viewModel.onOrientationChange()
            })

    }
}
