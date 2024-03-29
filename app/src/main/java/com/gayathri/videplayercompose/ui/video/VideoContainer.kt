package com.gayathri.videplayercompose.ui.video

import androidx.annotation.OptIn
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.media3.common.util.UnstableApi
import com.gayathri.ktor_client.model.Video
import com.gayathri.videplayercompose.home.PostUiActions
import com.gayathri.videplayercompose.ui.Loader
import com.gayathri.videplayercompose.videoplayer.VideoPlayerViewModel

@Composable
fun VideoContainer(viewModel: VideoPlayerViewModel, lifecycle: Lifecycle.Event) {
    val uiState by viewModel.uiState.collectAsState()
    // A surface container using the 'background' color from the theme
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        println("video_player_log $uiState")
        when (val playerUiState = uiState) {
            is VideoPlayerUiState.Loading -> Loader()
            is VideoPlayerUiState.Content -> PlayerContainerView(
                viewModel,
                playerUiState.video
            )

            else -> Unit
        }
    }
}

@OptIn(UnstableApi::class)
@Composable
fun PlayerContainerView(viewModel: VideoPlayerViewModel, video: Video) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        VideoView(viewModel = viewModel)
        VideoBottomView(video)
    }
}
