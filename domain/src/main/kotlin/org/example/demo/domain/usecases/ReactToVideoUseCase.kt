package org.example.demo.domain.usecases

import org.example.demo.domain.dtos.VideoReactionDto
import org.example.demo.domain.exceptions.InvalidVideoRefException
import org.example.demo.domain.exceptions.VideoNotFoundException
import org.example.demo.domain.models.VideoReaction
import org.example.demo.domain.models.VideoRef
import org.example.demo.domain.ports.CurrentUserInfo
import org.example.demo.domain.ports.TimeProvider
import org.example.demo.domain.ports.VideoReactionRepository
import org.example.demo.domain.ports.VideoRepository

class ReactToVideoUseCase(
    private val videoRepository: VideoRepository,
    private val reactionRepository: VideoReactionRepository,
    private val timeProvider: TimeProvider,
    private val currentUserInfo: CurrentUserInfo,
) {
    suspend fun execute(videoRefString: String, reaction: VideoReactionDto) {
        val videoRef = VideoRef.fromString(videoRefString) ?: throw InvalidVideoRefException(videoRefString)
        val video = videoRepository.getVideoByRef(videoRef) ?: throw VideoNotFoundException(videoRef)
        val userIdentity = currentUserInfo.getUserName()
        val now = timeProvider.now()
        val validatedReaction = VideoReaction(
            video.id,
            reaction.reaction,
            userIdentity,
            reaction.timestamp,
            now,
        )

        reactionRepository.addReaction(validatedReaction)
    }
}