package com.gayathri.videplayercompose.data.repository

import com.gayathri.ktor_client.model.Video
import com.gayathri.ktor_client.network.DataState
import com.gayathri.ktor_client.remote.ApiInterface
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepository @Inject constructor(private val api: ApiInterface) {
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
