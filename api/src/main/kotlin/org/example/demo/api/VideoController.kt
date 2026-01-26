package org.example.demo.api

import org.example.demo.domain.usecases.GetNextVideoUseCase
import org.example.demo.domain.usecases.GetVideoByIdUseCase
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/video")
class VideoController(
    private val getVideoById: GetVideoByIdUseCase,
    private val getNextVideo: GetNextVideoUseCase,
) {
    @GetMapping("/{videoId}")
    suspend fun getVideoById(@PathVariable videoId: UUID) = getVideoById.execute(videoId)

    @GetMapping("/next")
    suspend fun getNextVideo(@RequestParam seed: Long, @RequestParam index: Int) = getNextVideo.execute(seed, index)
}