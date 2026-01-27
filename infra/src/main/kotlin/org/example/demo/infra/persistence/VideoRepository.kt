package org.example.demo.infra.persistence

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.example.demo.domain.models.Video
import org.example.demo.domain.models.VideoRef
import org.example.demo.domain.ports.VideoRepository
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param

interface VideoJpaRepository : CrudRepository<VideoEntity, Int> {
    @Query("""
        SELECT new org.example.demo.domain.models.VideoRef(v.platformName, v.platformId)
        FROM VideoEntity v
    """)
    fun findAllRefs(): List<VideoRef>

    @Query("""
        SELECT v
        FROM VideoEntity v
        WHERE v.platformName = :#{#ref.platformName} AND v.platformId = :#{#ref.platformId}
    """)
    fun findByRef(@Param("ref") ref: VideoRef): VideoEntity?

    @Query("""
        SELECT v
        FROM VideoEntity v
        LEFT JOIN VideoReactionEntity r ON r.videoId = v.id
        GROUP BY v.id
        ORDER BY COUNT(r) DESC
    """)
    fun findTopVideosByReactionCount(pageable: Pageable): List<VideoEntity>
}

class JpaVideoRepository(private val jpaRepository: VideoJpaRepository) : VideoRepository {
    override suspend fun getVideoByRef(videoRef: VideoRef): Video? =
        withContext(Dispatchers.IO) {
            jpaRepository.findByRef(videoRef)?.toDomain()
        }

    override suspend fun getAllVideoRefs(): List<VideoRef> =
        withContext(Dispatchers.IO) {
            jpaRepository.findAllRefs()
        }

    override suspend fun getTrendingVideosByReactionCount(max: Int): List<Video> =
        withContext(Dispatchers.IO) {
            jpaRepository
                .findTopVideosByReactionCount(PageRequest.of(0, max))
                .map { it.toDomain() }
        }
}
