package com.gayathri.ktor_client.model

import kotlinx.serialization.Serializable

@Serializable
data class Video(
    val id: Int,
    val description: String,
    val source: String,
    val subtitle: String,
    val thumb: String,
    val title: String
)
