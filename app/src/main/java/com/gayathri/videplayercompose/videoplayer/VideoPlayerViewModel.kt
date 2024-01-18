package com.gayathri.videplayercompose.videoplayer

import android.os.Bundle
import android.util.Log
import androidx.annotation.OptIn
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import com.gayathri.ktor_client.AppConstant
import com.gayathri.videplayercompose.data.local.LikesEntity
import com.gayathri.videplayercompose.data.local.VideoDatabase
import com.gayathri.videplayercompose.data.local.mapToUiModel
import com.gayathri.videplayercompose.ui.video.VideoPlayerUiState
import com.gayathri.videplayercompose.ui.video.custom.PlayerProgressBarDataModel
import com.gayathri.videplayercompose.ui.video.custom.VideoPlayerControlAction
import com.gayathri.videplayercompose.ui.video.custom.state.PlayerOrientation
import com.gayathri.videplayercompose.ui.video.custom.state.PlayerState
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
) : ViewModel(), DefaultLifecycleObserver {

    private val _uiState = MutableStateFlow<VideoPlayerUiState>(VideoPlayerUiState.Loading)
    val uiState: StateFlow<VideoPlayerUiState> = _uiState.asStateFlow()

    private val _isPlaying = MutableStateFlow(true)
    val isPlaying: StateFlow<Boolean> = _isPlaying.asStateFlow()

    private val _playerDurationDataModel = MutableStateFlow(PlayerProgressBarDataModel())
    val playerDurationDataModel: StateFlow<PlayerProgressBarDataModel> =
        _playerDurationDataModel.asStateFlow()

    private val _playerState = MutableStateFlow<PlayerState>(PlayerState.NONE)
    val playerState: StateFlow<PlayerState> = _playerState.asStateFlow()

    private val _orientation = MutableStateFlow<PlayerOrientation>(PlayerOrientation.Portrait)
    val orientation: StateFlow<PlayerOrientation> = _orientation.asStateFlow()

    private val listener = object : Player.Listener {
        override fun onIsPlayingChanged(isPlayingValue: Boolean) {
            _isPlaying.value = isPlayingValue
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
            if (
                events.contains(Player.EVENT_PLAYBACK_STATE_CHANGED) ||
                events.contains(Player.EVENT_PLAY_WHEN_READY_CHANGED)
            ) {
                println("video_player_log : onEvents $events")
            }
        }

        override fun onPlaybackStateChanged(playbackState: Int) {
            super.onPlaybackStateChanged(playbackState)
            val state = when (playbackState) {
                Player.STATE_IDLE -> PlayerState.IDLE
                Player.STATE_BUFFERING -> PlayerState.BUFFERING
                Player.STATE_READY -> PlayerState.READY
                Player.STATE_ENDED -> PlayerState.ENDED
                else -> PlayerState.NONE
            }
            _playerState.update { state }
        }
    }

    init {
        println("video_player_log init")
        _uiState.update {
            VideoPlayerUiState.Loading
        }
        println("video_player_log prepare $uiState")
        player.prepare()
        addListeners()
    }

    @OptIn(UnstableApi::class)
    private fun addListeners() {
        println("video_player_log : addListeners")
        player.addListener(listener)
    }

    override fun onCleared() {
        super.onCleared()
        player.removeListener(listener)
        println("video_player_log : onCleared")
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

    fun onAction(playerControlAction: VideoPlayerControlAction) {
        when (playerControlAction) {
            is VideoPlayerControlAction.OnPlay -> player.play()
            is VideoPlayerControlAction.OnPause -> player.pause()
            is VideoPlayerControlAction.OnRewind -> player.seekBack()
            is VideoPlayerControlAction.OnForward -> player.seekForward()
            is VideoPlayerControlAction.OnSeekChanged -> player.seekTo(playerControlAction.timeMs.toLong())
        }
    }

    fun onOrientationChange() {
        val screenOrientation = if (orientation.value == PlayerOrientation.Landscape) {
            PlayerOrientation.Portrait
        } else {
            PlayerOrientation.Landscape
        }
        _orientation.update { screenOrientation }
    }

    override fun onStop(owner: LifecycleOwner) {
        super.onStop(owner)
        player.pause()
    }

    override fun onResume(owner: LifecycleOwner) {
        super.onResume(owner)
        player.play()
    }
}
