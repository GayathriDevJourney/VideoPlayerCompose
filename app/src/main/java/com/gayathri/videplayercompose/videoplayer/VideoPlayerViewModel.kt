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
import com.gayathri.videplayercompose.ui.video.custom.PlayerProgressBarDataModel
import com.gayathri.videplayercompose.ui.video.custom.VideoPlayerControlAction
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

    private val _isPlaying = MutableStateFlow(true)
    val isPlaying: StateFlow<Boolean> = _isPlaying.asStateFlow()

    private val _playerDurationDataModel = MutableStateFlow(PlayerProgressBarDataModel())
    val playerDurationDataModel: StateFlow<PlayerProgressBarDataModel> =
        _playerDurationDataModel.asStateFlow()

    init {
        println("video_player_log init")
        _uiState.update {
            VideoPlayerUiState.Loading
        }
        println("video_player_log prepare $uiState")
        player.prepare()
        addListeners()
    }

    private fun addListeners() {
        player.addListener(object : Player.Listener {
            override fun onIsPlayingChanged(isPlayingValue: Boolean) {
                _isPlaying.value = isPlayingValue
                println("video_player_log : isPlayingValue $isPlayingValue")
            }

            override fun onEvents(player: Player, events: Player.Events) {
                super.onEvents(player, events)
                _playerDurationDataModel.update {
                    _playerDurationDataModel.value.copy(
                        totalDuration = player.duration.coerceAtLeast(0L),
                        currentTime = player.currentPosition.coerceAtLeast(0L),
                        bufferedPercentage = player.bufferedPercentage
                    )
                }
            }
        })
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

    fun onAction(playerControlAction: VideoPlayerControlAction) {
        when (playerControlAction) {
            is VideoPlayerControlAction.OnPlay -> player.play()
            is VideoPlayerControlAction.OnPause -> player.pause()
            is VideoPlayerControlAction.OnRewind -> player.seekBack()
            is VideoPlayerControlAction.OnForward -> player.seekForward()
            is VideoPlayerControlAction.OnSeekChanged -> player.seekTo(playerControlAction.timeMs.toLong())
        }
    }
}
