package com.gayathri.videplayercompose.data.repository

import com.gayathri.ktor_client.model.Video
import com.gayathri.ktor_client.network.DataState
import com.gayathri.ktor_client.remote.ApiImpl
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MovieRepository {
    private val api = ApiImpl()
    fun getMovies(): Flow<DataState<List<Video>>> = flow {
        emit(DataState.Loading)
        delay(300L)
        try {
            val result = api.getMovies()
            emit(DataState.Success(result))
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }
}
