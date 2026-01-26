package org.example.demo.domain.usecases

import org.example.demo.domain.dtos.VideoDto
import org.example.demo.domain.ports.VideoRepository
import kotlin.random.Random

class GetNextVideoUseCase(private val repository: VideoRepository) {
    suspend fun execute(seed: Long, index: Int): VideoDto {
        val videoIds = repository.getAllVideoIds()
        videoIds.shuffle(Random(seed))
        val validIndex = index % (videoIds.size - 1)
        val videoId = videoIds[validIndex]
        val video = repository.getVideo(videoId) ?: throw Exception("Next video doesn't exist")

        return VideoDto(video.platformName, video.getVideoUrl())
    }
}