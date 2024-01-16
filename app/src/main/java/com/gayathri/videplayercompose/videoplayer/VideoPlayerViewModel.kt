package com.gayathri.videplayercompose.videoplayer

import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import com.gayathri.ktor_client.AppConstant
import com.gayathri.videplayercompose.data.local.LikesEntity
import com.gayathri.videplayercompose.data.local.VideoDatabase
import com.gayathri.videplayercompose.data.local.mapToUiModel
import com.gayathri.videplayercompose.ui.video.VideoPlayerUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VideoPlayerViewModel @Inject constructor(
    val player: Player,
    private val videoDatabase: VideoDatabase,
) : ViewModel() {

    private val _uiState = MutableStateFlow<VideoPlayerUiState>(VideoPlayerUiState.Loading)
    val uiState: StateFlow<VideoPlayerUiState> = _uiState.asStateFlow()

    init {
        println("video_player_log init")
        _uiState.update {
            VideoPlayerUiState.Loading
        }
        println("video_player_log prepare $uiState")
        player.prepare()
    }

    override fun onCleared() {
        super.onCleared()
        player.release()
    }


    fun setVideo(extras: Bundle?) {
        val videoId = extras?.getInt("videoId")
        println("video_player_log videoId $videoId")
        videoId?.let {
            viewModelScope.launch {
                val video = videoDatabase.videoDao().getVideo(videoId)
                println("video_player_log videoId $video.source")
                player.setMediaItem(MediaItem.fromUri(AppConstant.MEDIA_BASE_URL.plus(video.source)))
                player.play()
                _uiState.update {
                    VideoPlayerUiState.Content(video.mapToUiModel())
                }
            }
        }
    }

    fun postLike(videoId: Int, isLiked: Boolean) {
        viewModelScope.launch {
            videoDatabase.likesDao().insert(LikesEntity(videoId))
        }
    }

    fun getLikeStatus(videoId: Int) {
        viewModelScope.launch {
            videoDatabase.likesDao().getLikeStatus(videoId)
        }
    }
}
