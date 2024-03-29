package com.gayathri.videplayercompose.ui.video.custom

import kotlinx.serialization.Serializable

@Serializable
data class PlayerProgressBarDataModel(
    val totalDuration: Long = 0L,
    val currentTime: Long = 0L,
    val bufferedPercentage: Int = 0
)
