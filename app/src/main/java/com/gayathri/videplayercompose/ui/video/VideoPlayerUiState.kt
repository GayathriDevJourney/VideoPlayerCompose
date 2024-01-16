package com.gayathri.videplayercompose.ui.video

import com.gayathri.ktor_client.model.Video

sealed class VideoPlayerUiState {
    data class Error(val exception: Exception) : VideoPlayerUiState()
    data object Loading : VideoPlayerUiState()
    data object Loaded : VideoPlayerUiState()
    data class Content(val video: Video) : VideoPlayerUiState()
}
