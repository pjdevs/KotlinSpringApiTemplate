package org.example.demo.infra.persistence

import jakarta.persistence.Column
import org.example.demo.domain.models.Video
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.example.demo.domain.models.DraftVideo
import org.example.demo.domain.models.VideoId
import org.example.demo.domain.models.VideoPlatform
import org.example.demo.domain.models.VideoReaction
import kotlin.time.Instant

@Entity
@Table(name = "video")
class VideoEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    val id: Int? = null,
    @Column(name = "platform_name", nullable = false)
    val platformName: String = "",
    @Column(name = "platform_id", nullable = false)
    val platformId: String = "",
    @Column(name = "title", nullable = false)
    val title: String = "",
    @Column(name = "duration", nullable = false)
    val duration: Long = 0,
) {
    fun toDomain() = Video(
        if (id == null) error("Entity with no id cannot be converted to domain") else VideoId(id),
        VideoPlatform.fromPlatformName(platformName),
        platformId,
        title,
        duration,
    )
}

@Entity
@Table(name = "video_reaction")
class VideoReactionEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    val id: Int? = null,
    @Column(name = "videoId", nullable = false)
    val videoId: Int = 0,
    @Column(name = "reaction", nullable = false)
    val reaction: String = "",
    @Column(name = "userIdentity", nullable = false)
    val userIdentity: String = "",
    @Column(name = "timestamp", nullable = false)
    val timestamp: Long = 0,
    @Column(name = "date", nullable = false)
    val date: String = "",
) {
    fun toDomain() = VideoReaction(
        VideoId(videoId),
        reaction,
        userIdentity,
        timestamp,
        Instant.parse(date),
    )
}

fun DraftVideo.toEntity() : VideoEntity = VideoEntity(
    id = null,
    platformName = platform.toString(),
    platformId = platformId,
    title = title,
    duration = duration,
)

fun VideoReaction.toEntity() : VideoReactionEntity = VideoReactionEntity(
    null,
    videoId.id,
    reaction,
    userIdentity,
    timestamp,
    date.toString(),
)