package org.example.demo.domain.usecases

import org.example.demo.domain.dtos.VideoDto
import org.example.demo.domain.exceptions.InvalidStateException
import org.example.demo.domain.exceptions.InvalidVideoRefException
import org.example.demo.domain.ports.VideoRepository
import kotlin.random.Random

class GetNextVideoUseCase(private val repository: VideoRepository) {
    suspend fun execute(seed: Long, index: Int): VideoDto {
        val videoIds = repository.getAllVideoRefs()
        val shuffledIds = videoIds.shuffled(Random(seed))
        val validIndex = index % (videoIds.size - 1)
        val videoId = shuffledIds[validIndex]
        val video = repository.getVideoByRef(videoId)
            ?: throw InvalidStateException("Next video with id $videoId doesn't exist in database")

        return VideoDto.fromDomain(video)
    }
}