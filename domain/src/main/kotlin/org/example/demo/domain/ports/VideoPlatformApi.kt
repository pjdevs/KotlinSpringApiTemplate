package org.example.demo.domain.ports

import kotlin.time.Duration

interface VideoPlatformApi {
    suspend fun fetchIsVideoExisting(videoId: String) : Boolean
    suspend fun fetchVideoDuration(videoId: String) : Duration
}