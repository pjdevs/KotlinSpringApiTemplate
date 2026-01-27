package org.example.demo.domain.usecases

import org.example.demo.domain.dtos.VideoDto
import org.example.demo.domain.exceptions.InvalidVideoRefException
import org.example.demo.domain.exceptions.VideoNotFoundException
import org.example.demo.domain.models.VideoRef
import org.example.demo.domain.ports.VideoRepository

class GetVideoByIdUseCase(private val repository: VideoRepository) {
    suspend fun execute(videoRefString: String): VideoDto {
        val videoRef = VideoRef.fromString(videoRefString)
            ?: throw InvalidVideoRefException(videoRefString)
        val video = repository.getVideoByRef(videoRef) ?: throw VideoNotFoundException(videoRef)

        return VideoDto.fromDomain(video)
    }
}