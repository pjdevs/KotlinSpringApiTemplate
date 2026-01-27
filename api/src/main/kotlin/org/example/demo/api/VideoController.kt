package org.example.demo.api

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.example.demo.domain.dtos.ApiErrorDto
import org.example.demo.domain.dtos.VideoDto
import org.example.demo.domain.usecases.GetNextVideoUseCase
import org.example.demo.domain.usecases.GetTrendingVideosUseCase
import org.example.demo.domain.usecases.GetVideoByRefUseCase
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/videos", produces = [(MediaType.APPLICATION_JSON_VALUE)])
@Tag(name = "Video API v1", description = "Operations on videos (v1)")
class VideoController(
    private val getVideoById: GetVideoByRefUseCase,
    private val getNextVideo: GetNextVideoUseCase,
    private val getTrendingVideos: GetTrendingVideosUseCase,
) {
    @GetMapping("/{videoRef}")
    @Operation(
        summary = "Get a video by ID",
        description = "Version 1 of the API",
        tags = ["Video API v1"]
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
            description = "Video found",
            content = [
                Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = VideoDto::class)
                )
            ]
        )
    )
    suspend fun getVideoById(@PathVariable videoRef: String) = getVideoById.execute(videoRef)

    @GetMapping("/next")
    @Operation(
        summary = "Get the video at index given a seed for stateless shuffle/scroll",
        description = "Version 1 of the API",
        tags = ["Video API v1"]
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
            responseCode = "200",
            description = "Successfully got next video in the sequence",
            content = [
                Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = VideoDto::class)
                )
            ]
        )
    )
    suspend fun getNextVideo(@RequestParam seed: Long, @RequestParam index: Int) = getNextVideo.execute(seed, index)

    @GetMapping("/trending")
    @Operation(
        summary = "Get a list of the trending videos with most reactions",
        description = "Version 1 of the API",
        tags = ["Video API v1"]
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
            responseCode = "200",
            description = "Successfully fetched trending videos",
            content = [
                Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = Array<VideoDto>::class)
                )
            ]
        )
    )
    suspend fun getTrendingVideos() = getTrendingVideos.execute()
}