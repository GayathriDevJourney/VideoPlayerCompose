package com.gayathri.videplayercompose.ui.home

import com.gayathri.ktor_client.model.Video
import java.lang.Exception

sealed class HomeUiState {
    data class Content(val data: List<Video>) : HomeUiState()
    data class Error(val exception: Exception) : HomeUiState()
    data object Loading : HomeUiState()
}
