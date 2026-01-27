package org.example.demo.domain.dtos

import org.example.demo.domain.models.Video

data class VideoDto(val platformName: String, val url: String) {
    companion object {
        fun fromDomain(video: Video): VideoDto {
            return VideoDto(video.platformName, video.getVideoUrl())
        }
    }
}