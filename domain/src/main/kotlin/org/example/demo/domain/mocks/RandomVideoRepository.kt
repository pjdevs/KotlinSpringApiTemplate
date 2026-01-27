package org.example.demo.domain.mocks

import org.example.demo.domain.models.Video
import org.example.demo.domain.models.VideoId
import org.example.demo.domain.models.VideoRef
import org.example.demo.domain.ports.VideoRepository
import kotlin.math.max

class RandomVideoRepository : VideoRepository {
    private val videos = arrayOf(
        Video(VideoId(0), "youtube", "hbHgIzIbzmQ", "Micode"),
        Video(VideoId(1), "youtube", "sB_zRkqnfd0", "Yolo"),
        Video(VideoId(2), "youtube", "oVHpiQdd4iI", "Test"),
    )

    override suspend fun getVideoByRef(videoRef: VideoRef) = videos.find { it.getRef() == videoRef }
    override suspend fun getAllVideoRefs() = videos.map { it.getRef() }
    override suspend fun getTrendingVideosByReactionCount(max: Int)
        = videos.slice(0..max(max, videos.size - 1))
}