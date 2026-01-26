package org.example.demo.domain

import kotlinx.coroutines.runBlocking
import org.example.demo.domain.mocks.RandomVideoRepository
import kotlin.test.Test
import kotlin.test.assertNotEquals
import kotlin.test.assertNotNull

class RandomVideoRepositoryTests {
    private val videoRepository = RandomVideoRepository()

    @Test
    fun repoReturnsNonNullAndValidVideos() {
        val videoIds = runBlocking { videoRepository.getAllVideoIds() }

        assertNotEquals(0, videoIds.size)

        for (videoId in videoIds) {
            val video = runBlocking { videoRepository.getVideo(videoId) }
            assertNotNull(video)
        }
    }
}