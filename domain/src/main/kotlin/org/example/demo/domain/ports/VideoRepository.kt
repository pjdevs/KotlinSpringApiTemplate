package org.example.demo.domain.ports

import org.example.demo.domain.models.Video
import org.example.demo.domain.models.VideoId
import org.example.demo.domain.models.VideoRef

interface VideoRepository {
    suspend fun getVideoByRef(videoRef: VideoRef): Video?
    suspend fun getAllVideoRefs(): Array<VideoRef>
}