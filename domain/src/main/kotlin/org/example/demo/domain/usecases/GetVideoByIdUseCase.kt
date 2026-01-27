package org.example.demo.domain.usecases

import org.example.demo.domain.dtos.VideoDto
import org.example.demo.domain.models.VideoRef
import org.example.demo.domain.ports.VideoRepository

class GetVideoByIdUseCase(private val repository: VideoRepository) {
    suspend fun execute(videoRefString: String): VideoDto {
        val videoRef = VideoRef.fromString(videoRefString) ?: throw Exception("Malformed video ref $videoRefString")
        val video = repository.getVideoByRef(videoRef) ?: throw Exception("Video with ref $videoRef not found")
        return VideoDto(video.platformName, video.getVideoUrl())
    }
}