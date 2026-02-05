package org.example.demo.domain.models

@JvmInline
value class VideoId(val id: Int)

class Video(
    val id: VideoId,
    val platform: VideoPlatform,
    val platformId: String,
    val title: String,
    val duration: Long,
) {
    fun getVideoUrl() : String
        = when (platform) {
            VideoPlatform.YOUTUBE -> "https://www.youtube.com/embed/$platformId"
        }

    fun getRef() : VideoRef {
        return VideoRef(platform, platformId)
    }
}

class VideoRef(val platform: VideoPlatform, val platformId: String) {
    companion object {
        @JvmStatic
        fun fromString(videoRef: String): VideoRef? {
            val parts = videoRef.split(":")

            if (parts.size != 2) {
                return null
            }

            return VideoRef(parts[0], parts[1])
        }
    }

    constructor(platformName: String, platformId: String)
            : this(VideoPlatform.fromPlatformName(platformName), platformId)

    init {
        require(platformId.isNotEmpty()) { "platformId must not be empty" }
    }

    override fun toString(): String = "$platform:$platformId"
}

class DraftVideo(val platform: VideoPlatform, val platformId: String, val title: String, val duration: Long)