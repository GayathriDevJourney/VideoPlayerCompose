package com.gayathri.videplayercompose.videoplayer

import android.os.Bundle
import androidx.annotation.OptIn
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.HttpDataSource
import androidx.media3.exoplayer.ExoPlayer
import com.gayathri.ktor_client.AppConstant
import com.gayathri.ktor_client.model.Video
import com.gayathri.videplayercompose.data.local.VideoDatabase
import com.gayathri.videplayercompose.data.local.VideoEntity
import com.gayathri.videplayercompose.data.local.mapToUiModel
import com.gayathri.videplayercompose.ui.video.VideoPlayerUiState
import com.gayathri.videplayercompose.ui.video.custom.PlayerProgressBarDataModel
import com.gayathri.videplayercompose.ui.video.custom.VideoPlayerControlAction
import com.gayathri.videplayercompose.ui.video.custom.state.PlayerOrientation
import com.gayathri.videplayercompose.ui.video.custom.state.PlayerState
import com.gayathri.videplayercompose.utils.TAG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class VideoPlayerViewModel @Inject constructor(
    val player: ExoPlayer,
    private val videoDatabase: VideoDatabase,
) : ViewModel(), DefaultLifecycleObserver {

    private lateinit var playlistData: List<Video>
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
            println("video_player_log : onEvents $events")
        }

        override fun onPlaybackStateChanged(playbackState: Int) {
            val state = when (playbackState) {
                Player.STATE_IDLE -> PlayerState.IDLE
                Player.STATE_BUFFERING -> PlayerState.BUFFERING
                Player.STATE_READY -> PlayerState.READY
                Player.STATE_ENDED -> PlayerState.ENDED
                else -> PlayerState.NONE
            }
            println("video_player_log : onPlaybackStateChanged $playbackState")
            _playerState.update { state }
            super.onPlaybackStateChanged(playbackState)
        }

        override fun onPlayerError(error: PlaybackException) {
            super.onPlayerError(error)
            println("$TAG : error ${error.message}")
            val cause = error.cause
            if (cause is HttpDataSource.HttpDataSourceException) {
                println("$TAG : error HttpDataSourceException")
                // An HTTP error occurred.
                // It's possible to find out more about the error both by casting and by querying
                // the cause.
                if (cause is HttpDataSource.InvalidResponseCodeException) {
                    // Cast to InvalidResponseCodeException and retrieve the response code, message
                    // and headers.
                    println("$TAG : error InvalidResponseCodeException")
                } else {
                    // Try calling httpError.getCause() to retrieve the underlying cause, although
                    // note that it may be null.
                    println("$TAG : error ${cause.cause}")
                }
            }
        }

        override fun onMediaMetadataChanged(mediaMetadata: MediaMetadata) {
            super.onMediaMetadataChanged(mediaMetadata)
            println("$TAG : mediaMetadata onMediaMetadataChanged")
        }

        override fun onPlaylistMetadataChanged(mediaMetadata: MediaMetadata) {
            super.onPlaylistMetadataChanged(mediaMetadata)
            println("$TAG : mediaMetadata onPlaylistMetadataChanged")
        }

        override fun onMediaItemTransition(mediaItem: MediaItem?, reason: Int) {
            super.onMediaItemTransition(mediaItem, reason)
            println("$TAG : mediaMetadata onMediaItemTransition ${mediaItem?.mediaId} $reason")
            getCurrentPlayingMetaData(mediaItem)
        }
    }

    private fun getCurrentPlayingMetaData(mediaItem: MediaItem?) {
        if (::playlistData.isInitialized) {
            playlistData.find {
                it.id.toString() == mediaItem?.mediaId
            }?.let { video ->
                _uiState.update {
                    VideoPlayerUiState.Content(video)
                }
            }
        }
    }

    init {
        _uiState.update { VideoPlayerUiState.Loading }
        player.prepare()
        addListeners()
    }

    @OptIn(UnstableApi::class)
    private fun addListeners() {
        player.addListener(listener)
    }

    override fun onCleared() {
        super.onCleared()
        player.removeListener(listener)
        player.release()
    }


    fun setVideo(extras: Bundle?) {
        val videoId = extras?.getInt("videoId")
        videoId?.let {
            viewModelScope.launch {
                val video = videoDatabase.videoDao().getVideo(videoId)
                player.setMediaItem(MediaItem.fromUri(AppConstant.MEDIA_BASE_URL.plus(video.source)))
                player.play()
                _uiState.update {
                    VideoPlayerUiState.Content(video.mapToUiModel())
                }
            }
            createPlaylist(videoId)
        }
    }

    private fun createPlaylist(videoId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val playlistMediaItem = mutableListOf<MediaItem>()
            val mediaItems = videoDatabase.videoDao().getVideos().filter {
                it.id > videoId
            }.map {
                playlistMediaItem.add(createMediaItem(it))
                it.mapToUiModel()
            }
            val prevMediaItems = videoDatabase.videoDao().getVideos().filter {
                it.id < videoId
            }.map {
                playlistMediaItem.add(createMediaItem(it))
                it.mapToUiModel()
            }
            withContext(Dispatchers.Main) {
                player.addMediaItems(playlistMediaItem)
            }
            playlistData = mediaItems.plus(prevMediaItems)
        }
    }

    private fun createMediaItem(video: VideoEntity): MediaItem {
        // Build a media item with a media ID.
        with(video.mapToUiModel()) {
            val uri = AppConstant.MEDIA_BASE_URL.plus(source)
            return MediaItem.Builder().setUri(uri).setMediaId(id.toString()).build()
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
