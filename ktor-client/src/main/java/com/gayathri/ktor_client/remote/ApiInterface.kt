package com.gayathri.ktor_client.remote

import com.gayathri.ktor_client.model.Video

interface ApiInterface {
    suspend fun getMovies(): List<Video>
}