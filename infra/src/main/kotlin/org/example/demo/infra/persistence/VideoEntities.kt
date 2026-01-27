package org.example.demo.infra.persistence

import org.example.demo.domain.models.Video;
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.example.demo.domain.models.VideoId
import org.example.demo.domain.models.VideoReaction
import kotlin.time.Instant
import java.util.UUID

@Entity
@Table(name = "video")
class VideoEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    val id: Long?,
    val platformName: String,
    val platformId: String,
    val title: String,
) {
    constructor() : this(null, "", "", "")

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
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    val id: Long?,
    val videoId: Long,
    val reaction: String,
    val userIdentity: String,
    val timestamp: Long,
    val date: Instant,
) {
    constructor() : this(null, 0, "", "", 0, Instant.DISTANT_PAST)

    fun toDomain() = VideoReaction(
        VideoId(videoId),
        reaction,
        userIdentity,
        timestamp,
        date,
    )
}

fun VideoReaction.toEntity() : VideoReactionEntity = VideoReactionEntity(
    null,
    videoId.id,
    reaction,
    userIdentity,
    timestamp,
    date,
)