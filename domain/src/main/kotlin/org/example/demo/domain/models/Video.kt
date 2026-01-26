package org.example.demo.domain.models

import java.util.UUID

@JvmInline
value class VideoId(val id: UUID)

class Video(val id: VideoId?, val platformName: String, val platformId: String) {
    fun getVideoUrl() : String
        = when (platformName) {
            "youtube" -> "https://www.youtube.com/embed/$platformId"
            else -> "unknown"
        }
}