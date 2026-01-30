package org.example.demo.domain.usecases

import org.example.demo.domain.exceptions.VideoNotFoundException
import org.example.demo.domain.models.DraftVideo
import org.example.demo.domain.models.VideoRef
import org.example.demo.domain.ports.VideoPlatformApiFactory
import org.example.demo.domain.ports.VideoRepository

class AddVideoUseCase(
    val videoRepository: VideoRepository,
    val videoPlatformApiFactory: VideoPlatformApiFactory,
) {
    suspend fun execute(videoRef: VideoRef) {
        val platformApi = videoPlatformApiFactory.create(videoRef.platform)
        val isVideoExisting = platformApi.fetchIsVideoExisting(videoRef.platformId)

        if (!isVideoExisting) {
            throw VideoNotFoundException(videoRef)
        }

        val title = platformApi.fetchVideoTitle(videoRef.platformId)
        val duration = platformApi.fetchVideoDuration(videoRef.platformId)
        val draftVideo = DraftVideo(videoRef.platform, videoRef.platformId, title, duration.inWholeSeconds)

        // Could send back result via a DTO but for simplicity send nothing
        videoRepository.saveVideo(draftVideo)
    }
}