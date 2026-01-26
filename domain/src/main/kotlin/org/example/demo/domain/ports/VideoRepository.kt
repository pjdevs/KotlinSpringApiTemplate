package org.example.demo.domain.ports

import org.example.demo.domain.models.Video
import org.example.demo.domain.models.VideoId

interface VideoRepository {
    suspend fun getVideo(videoId : VideoId): Video?
    suspend fun getAllVideoIds(): Array<VideoId>
}