package org.example.demo.api

import org.example.demo.domain.dtos.VideoReactionDto
import org.example.demo.domain.usecases.ReactToVideoUseCase
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/video/{videoId}")
class ReactionController(
    private val reactToVideo: ReactToVideoUseCase,
) {
    @PostMapping("/react")
    suspend fun reactToVideo(
        @PathVariable videoId: UUID,
        @RequestBody reaction: VideoReactionDto
    ) = reactToVideo.execute(videoId, reaction)
}