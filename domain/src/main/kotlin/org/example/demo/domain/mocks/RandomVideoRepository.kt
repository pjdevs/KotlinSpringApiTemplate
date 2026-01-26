package org.example.demo.domain.mocks

import org.example.demo.domain.models.Video
import org.example.demo.domain.models.VideoId
import org.example.demo.domain.ports.VideoRepository
import java.util.UUID

class RandomVideoRepository : VideoRepository {
    private val videos = arrayOf(
        Video(VideoId(UUID.randomUUID()), "youtube", "hbHgIzIbzmQ"),
        Video(VideoId(UUID.randomUUID()), "youtube", "sB_zRkqnfd0"),
        Video(VideoId(UUID.randomUUID()), "youtube", "oVHpiQdd4iI")
    )

    override suspend fun getVideo(videoId: VideoId) = videos.find { it.id == videoId }

    override suspend fun getAllVideoIds() = videos.map { it.id!! }.toTypedArray()
}