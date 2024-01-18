package com.gayathri.videplayercompose.ui.video.custom.state

sealed class PlayerState {
    data object IDLE : PlayerState()
    data object BUFFERING : PlayerState()
    data object READY : PlayerState()
    data object ENDED : PlayerState()
    data object NONE : PlayerState()
    data object PLAYING : PlayerState()
    data object PAUSE : PlayerState()
}
