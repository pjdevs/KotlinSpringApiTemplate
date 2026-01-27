package org.example.demo.infra.persistence

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.example.demo.domain.models.Video
import org.example.demo.domain.models.VideoRef
import org.example.demo.domain.ports.VideoRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import java.util.UUID

interface VideoJpaRepository : CrudRepository<VideoEntity, UUID> {
    @Query("SELECT new org.example.demo.domain.models.VideoRef(v.platformName, v.platformId) FROM VideoEntity v")
    fun findAllRefs(): List<VideoRef>

    @Query("SELECT v FROM VideoEntity v WHERE v.platformName = :#{#ref.platformName} AND v.platformId = :#{#ref.platformId}")
    fun findByRef(@Param("ref") ref: VideoRef): VideoEntity?
}

class JpaVideoRepository(private val jpaRepository: VideoJpaRepository) : VideoRepository {
    override suspend fun getVideoByRef(videoRef: VideoRef): Video? =
        withContext(Dispatchers.IO) {
            jpaRepository.findByRef(videoRef)?.toDomain()
        }


    override suspend fun getAllVideoRefs(): Array<VideoRef> =
        withContext(Dispatchers.IO) {
            jpaRepository.findAllRefs().toTypedArray()
        }
}