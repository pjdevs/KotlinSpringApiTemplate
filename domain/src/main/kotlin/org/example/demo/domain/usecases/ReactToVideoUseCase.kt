package org.example.demo.domain.usecases

import org.example.demo.domain.dtos.VideoReactionDto
import org.example.demo.domain.exceptions.InvalidReactionException
import org.example.demo.domain.exceptions.InvalidStateException
import org.example.demo.domain.exceptions.InvalidVideoRefException
import org.example.demo.domain.exceptions.VideoNotFoundException
import org.example.demo.domain.models.VideoReaction
import org.example.demo.domain.models.VideoRef
import org.example.demo.domain.ports.CurrentUserInfo
import org.example.demo.domain.ports.DomainEventPublisher
import org.example.demo.domain.ports.TimeProvider
import org.example.demo.domain.ports.VideoPlatformApiFactory
import org.example.demo.domain.ports.VideoReactionRepository
import org.example.demo.domain.ports.VideoRepository
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

class ReactToVideoUseCase(
    private val videoRepository: VideoRepository,
    private val reactionRepository: VideoReactionRepository,
    private val timeProvider: TimeProvider,
    private val currentUserInfo: CurrentUserInfo,
    private val domainEventPublisher: DomainEventPublisher,
    private val videoPlatformApiFactory: VideoPlatformApiFactory,
) {
    suspend fun execute(videoRefString: String, reaction: VideoReactionDto) {
        // Validate input
        val videoRef = VideoRef.fromString(videoRefString) ?: throw InvalidVideoRefException(videoRefString)
        val video = videoRepository.getVideoByRef(videoRef) ?: throw VideoNotFoundException(videoRef)

        // Validate via platform API
        // (totally fake, the only place we could validate video via platform API is on creation)
        // (but this is for demonstration purposes)
        val api = videoPlatformApiFactory.create(video.platform)
        val isVideoExisting: Boolean
        val videoDuration: Duration

        try {
            isVideoExisting = api.fetchIsVideoExisting(video.platformId)
            videoDuration = api.fetchVideoDuration(video.platformId)
        } catch (e: Exception) {
            // put the operation in retry queue etc. if it was network failure
            // here we just return 500
            throw InvalidStateException("Could not contact platform API for ${video.platform}: ${e.message}")
        }

        if (!isVideoExisting) {
            throw VideoNotFoundException(videoRef)
        }

        if (reaction.timestamp < 0 || reaction.timestamp.seconds > videoDuration) {
            throw InvalidReactionException("Timestamp ${reaction.timestamp} is out of video duration bounds")
        }

        // Create the reaction
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

        // Publish event
        domainEventPublisher.publish("REACTION_CREATED: $validatedReaction")
    }
}