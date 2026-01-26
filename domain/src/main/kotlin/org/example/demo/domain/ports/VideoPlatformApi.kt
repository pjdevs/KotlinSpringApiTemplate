package org.example.demo.domain.ports

import org.example.demo.domain.models.Video
import kotlin.time.Duration

interface VideoPlatformApi {
    suspend fun getVideoDuration(video: Video) : Duration
}