package org.example.demo.domain.usecases

import org.example.demo.domain.dtos.VideoDto
import org.example.demo.domain.ports.VideoRepository

class GetTrendingVideosUseCase(private val repository: VideoRepository) {
    private val trendingVideoCount = 10

    suspend fun execute(): List<VideoDto> {
        val videos = repository.getTrendingVideosByReactionCount(trendingVideoCount)

        return videos.map { VideoDto.fromDomain(it) }
    }
}