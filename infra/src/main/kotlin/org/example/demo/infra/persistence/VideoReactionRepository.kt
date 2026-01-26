package org.example.demo.infra.persistence

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.example.demo.domain.models.VideoId
import org.example.demo.domain.models.VideoReaction
import org.example.demo.domain.ports.VideoReactionRepository
import org.springframework.data.repository.CrudRepository
import java.util.UUID

interface VideoReactionJpaRepository : CrudRepository<VideoReactionEntity, UUID>

class JpaVideoReactionRepository(private val jpaRepository: VideoReactionJpaRepository) : VideoReactionRepository {
    override suspend fun addReaction(reaction: VideoReaction): VideoReaction =
        withContext(Dispatchers.IO) {
            val savedEntity = jpaRepository.save(reaction.toEntity())
            savedEntity.toDomain()
        }

    override suspend fun getAllReactionsFor(videoId: VideoId): Array<VideoReaction> =
        withContext(Dispatchers.IO) {
            jpaRepository.findAll().map { it.toDomain() }.toTypedArray()
        }
}