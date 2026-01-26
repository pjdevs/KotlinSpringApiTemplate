package org.example.demo.domain.usecases

import org.example.demo.domain.dtos.VideoDto
import org.example.demo.domain.models.VideoId
import org.example.demo.domain.ports.VideoRepository
import java.util.UUID
import kotlin.random.Random

class GetVideoByIdUseCase(private val repository: VideoRepository) {
    suspend fun execute(videoId: UUID): VideoDto {
        val video = repository.getVideo(VideoId(videoId)) ?: throw Exception("Video with id $videoId not found")
        return VideoDto(video.platformName, video.getVideoUrl())
    }
}