package org.example.demo.api

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import org.example.demo.domain.dtos.ApiErrorDto
import org.example.demo.domain.dtos.VideoReactionDto
import org.example.demo.domain.usecases.ReactToVideoUseCase
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/videos/{videoRef}")
@Tag(name = "Reaction API v1", description = "Operations related to reactions (v1)")
class ReactionController(
    private val reactToVideo: ReactToVideoUseCase,
) {
    @PostMapping("/react")
    @Operation(
        summary = "Create a new reaction to a video",
        description = "Version 1 of the API",
        tags = ["Reaction API v1"]
    )
    @ApiResponses(
        ApiResponse(
            responseCode = "500",
            description = "Internal Server Error",
            content = [
                Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = ApiErrorDto::class)
                )
            ]
        ),
        ApiResponse(
            responseCode = "404",
            description = "Video not found",
            content = [
                Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = ApiErrorDto::class)
                )
            ]
        ),
        ApiResponse(
            responseCode = "400",
            description = "Video ref is invalid",
            content = [
                Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = ApiErrorDto::class)
                )
            ]
        ),
        ApiResponse(
            responseCode = "200",
            description = "Reaction created",
            content = []
        )
    )
    suspend fun reactToVideo(
        @PathVariable videoRef: String,
        @RequestBody reaction: VideoReactionDto
    ) = reactToVideo.execute(videoRef, reaction)
}