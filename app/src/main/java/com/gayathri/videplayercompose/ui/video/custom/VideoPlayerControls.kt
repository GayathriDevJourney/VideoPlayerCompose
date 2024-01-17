package com.gayathri.videplayercompose.ui.video.custom

sealed class VideoPlayerControlAction {
    data object OnPlay : VideoPlayerControlAction()
    data object OnPause : VideoPlayerControlAction()
    data object OnRewind : VideoPlayerControlAction()
    data object OnForward : VideoPlayerControlAction()
    data class OnSeekChanged(val timeMs: Float) : VideoPlayerControlAction()
}