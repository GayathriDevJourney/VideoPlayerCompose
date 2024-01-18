package com.gayathri.videplayercompose.ui.video.custom.state

sealed class PlayerOrientation {
    data object Landscape : PlayerOrientation()
    data object Portrait : PlayerOrientation()
}
