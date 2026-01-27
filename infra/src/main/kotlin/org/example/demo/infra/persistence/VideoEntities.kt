package org.example.demo.infra.persistence

import jakarta.persistence.Column
import org.example.demo.domain.models.Video;
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.example.demo.domain.models.VideoId
import org.example.demo.domain.models.VideoReaction
import java.sql.Timestamp
import kotlin.time.Instant
import java.util.UUID
import kotlin.time.toJavaInstant
import kotlin.time.toKotlinInstant

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
) {
    fun toDomain() = Video(
        if (id == null) error("Entity with no id cannot be converted to domain") else VideoId(id),
        platformName,
        platformId,
        title,
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

fun VideoReaction.toEntity() : VideoReactionEntity = VideoReactionEntity(
    null,
    videoId.id,
    reaction,
    userIdentity,
    timestamp,
    date.toString(),
)