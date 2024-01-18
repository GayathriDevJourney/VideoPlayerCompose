package com.gayathri.videplayercompose.ui.video

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.media3.ui.PlayerView
import com.gayathri.videplayercompose.videoplayer.VideoPlayerViewModel

@Composable
fun VideoView(viewModel: VideoPlayerViewModel, lifecycle: Lifecycle.Event) {
    AndroidView(
        factory = { context ->
            PlayerView(context).also {
                it.player = viewModel.player
            }
        }, modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(16 / 9f),
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
}
