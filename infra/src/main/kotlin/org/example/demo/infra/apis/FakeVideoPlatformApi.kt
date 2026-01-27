package org.example.demo.infra.apis

import org.example.demo.domain.ports.VideoPlatformApi
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

class FakeVideoPlatformApi : VideoPlatformApi {

    override suspend fun fetchIsVideoExisting(videoId: String): Boolean = true
    override suspend fun fetchVideoDuration(videoId: String): Duration = 180.seconds

}