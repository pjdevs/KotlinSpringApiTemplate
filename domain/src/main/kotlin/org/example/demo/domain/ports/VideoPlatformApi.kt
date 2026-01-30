package org.example.demo.domain.ports

import org.example.demo.domain.exceptions.VideoPlatformApiException
import org.example.demo.domain.models.VideoPlatform
import kotlin.time.Duration

interface VideoPlatformApi {
    val videoPlatform: VideoPlatform

    @Throws(VideoPlatformApiException::class)
    suspend fun fetchIsVideoExisting(videoId: String) : Boolean
    @Throws(VideoPlatformApiException::class)
    suspend fun fetchVideoDuration(videoId: String) : Duration
    @Throws(VideoPlatformApiException::class)
    suspend fun fetchVideoTitle(videoId: String) : String
}