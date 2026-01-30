package org.example.demo.domain.ports

import org.example.demo.domain.models.DraftVideo
import org.example.demo.domain.models.Video
import org.example.demo.domain.models.VideoRef

interface VideoRepository {
    suspend fun getVideoByRef(videoRef: VideoRef): Video?
    suspend fun getAllVideoRefs(): List<VideoRef>
    suspend fun getTrendingVideosByReactionCount(max: Int): List<Video>
    suspend fun saveVideo(video: DraftVideo): Video
}