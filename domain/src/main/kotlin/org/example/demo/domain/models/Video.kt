package org.example.demo.domain.models

@JvmInline
value class VideoId(val id: Long)

class Video(val id: VideoId, val platformName: String, val platformId: String, val title: String) {
    fun getVideoUrl() : String
        = when (platformName) {
            "youtube" -> "https://www.youtube.com/embed/$platformId"
            else -> "unknown"
        }

    fun getRef() : VideoRef {
        return VideoRef(platformName, platformId)
    }
}

data class VideoRef(val platformName: String, val platformId: String) {
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

    override fun toString(): String = "$platformName:$platformId"
}