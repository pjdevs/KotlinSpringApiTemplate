package org.example.demo.domain.models

import kotlin.time.Instant

data class VideoReaction(
    val videoId: VideoId,
    val reaction: String,
    val userIdentity: String,
    val timestamp: Long,
    val date: Instant
)