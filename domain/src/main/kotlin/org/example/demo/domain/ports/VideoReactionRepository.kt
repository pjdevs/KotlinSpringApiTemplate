package org.example.demo.domain.ports

import org.example.demo.domain.models.VideoId
import org.example.demo.domain.models.VideoReaction

interface VideoReactionRepository {
    suspend fun addReaction(reaction: VideoReaction): VideoReaction
    suspend fun getAllReactionsFor(videoId: VideoId) : Array<VideoReaction>
}