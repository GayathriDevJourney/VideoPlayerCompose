package com.gayathri.ktor_client.remote

import com.gayathri.ktor_client.model.Video
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.encodedPath
import kotlinx.serialization.json.Json
import javax.inject.Inject

class ApiImpl @Inject constructor() : ApiInterface {

    override suspend fun getMovies(): List<Video> {
        val data = client.get {
            url {
                encodedPath = "sample-videos.json"
            }
        }.body<String>()
        return Json.decodeFromString<List<Video>>(data)
    }
}
