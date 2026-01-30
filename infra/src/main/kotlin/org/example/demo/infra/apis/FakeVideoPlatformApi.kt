package org.example.demo.infra.apis

import org.example.demo.domain.exceptions.VideoPlatformApiException
import org.example.demo.domain.models.VideoPlatform
import org.example.demo.domain.ports.VideoPlatformApi
import kotlin.random.Random
import kotlin.random.nextInt
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

class FakeVideoPlatformApi(override val videoPlatform: VideoPlatform) : VideoPlatformApi {

    override suspend fun fetchIsVideoExisting(videoId: String): Boolean {
        // Simulate failure 20% of the time
        if (Random.nextInt(1..10) > 8) {
            throw VideoPlatformApiException("Platform API '$videoPlatform' can't be reached")
        }

        return true
    }
    override suspend fun fetchVideoDuration(videoId: String): Duration = 180.seconds
    override suspend fun fetchVideoTitle(videoId: String): String = "Super video title"
}