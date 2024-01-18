package com.gayathri.videplayercompose.ui.video

import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.gayathri.videplayercompose.home.ObserveLifecycleEvents
import com.gayathri.videplayercompose.videoplayer.VideoPlayerViewModel

@Composable
fun VideoPlayerPage(
    extras: Bundle?,
    viewModel: VideoPlayerViewModel = hiltViewModel<VideoPlayerViewModel>()
) {
    println("video_player_log VideoPlayerPage")
    viewModel.setVideo(extras)

    var lifecycle by remember { mutableStateOf(Lifecycle.Event.ON_CREATE) }
    val lifecycleOwner = LocalLifecycleOwner.current


    /*DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            lifecycle = event
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }*/
    viewModel.ObserveLifecycleEvents(LocalLifecycleOwner.current.lifecycle)
    VideoContainer(viewModel, lifecycle)
}