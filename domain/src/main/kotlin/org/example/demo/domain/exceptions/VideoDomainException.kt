package org.example.demo.domain.exceptions

import org.example.demo.domain.models.VideoRef

sealed class VideoDomainException(message: String) : RuntimeException(message)

class InvalidStateException(stateDesc: String) : VideoDomainException(stateDesc)

class InvalidVideoRefException(ref: String) : VideoDomainException("Invalid video ref: $ref")

class VideoNotFoundException(ref: VideoRef) : VideoDomainException("Video not found: $ref")

class InvalidReactionException(reaction: String) : VideoDomainException("Invalid reaction: $reaction")