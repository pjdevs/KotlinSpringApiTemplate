package org.example.demo.domain.usecases

import org.example.demo.domain.dtos.VideoReactionDto
import org.example.demo.domain.models.VideoId
import org.example.demo.domain.models.VideoReaction
import org.example.demo.domain.ports.CurrentUserInfo
import org.example.demo.domain.ports.TimeProvider
import org.example.demo.domain.ports.VideoReactionRepository
import org.example.demo.domain.ports.VideoRepository
import java.util.UUID

class ReactToVideoUseCase(
    private val videoRepository: VideoRepository,
    private val reactionRepository: VideoReactionRepository,
    private val timeProvider: TimeProvider,
    private val currentUserInfo: CurrentUserInfo,
) {
    suspend fun execute(videoId: UUID, reaction: VideoReactionDto) {
        val video = videoRepository.getVideo(VideoId(videoId)) ?: throw Exception("Video with id $videoId not found")
        val videoId = video.id!!
        val userIdentity = currentUserInfo.getUserName()
        val now = timeProvider.now()
        val validatedReaction = VideoReaction(
            videoId,
            reaction.reaction,
            userIdentity,
            reaction.timestamp,
            now,
        )

        reactionRepository.addReaction(validatedReaction)
    }
}