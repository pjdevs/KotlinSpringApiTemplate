package org.example.demo.infra.persistence

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.example.demo.domain.models.Video
import org.example.demo.domain.models.VideoId
import org.example.demo.domain.ports.VideoRepository
import org.springframework.data.repository.CrudRepository
import java.util.UUID

interface VideoJpaRepository : CrudRepository<VideoEntity, UUID>

class JpaVideoRepository(private val jpaRepository: VideoJpaRepository) : VideoRepository {
    override suspend fun getVideo(videoId: VideoId): Video? =
        withContext(Dispatchers.IO) {
            jpaRepository
                .findById(videoId.id)
                .orElse(null)
                ?.toDomain()
        }


    override suspend fun getAllVideoIds(): Array<VideoId> =
        withContext(Dispatchers.IO) {
            jpaRepository.findAll().map { VideoId(it.id!!) }.toTypedArray()
        }
}