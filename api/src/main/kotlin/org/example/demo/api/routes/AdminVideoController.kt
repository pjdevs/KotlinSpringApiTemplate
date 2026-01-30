package org.example.demo.api.routes

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import org.example.demo.domain.dtos.ApiErrorDto
import org.example.demo.domain.models.VideoRef
import org.example.demo.domain.usecases.AddVideoUseCase
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/videos")
@Tag(name = "Admin Video API v1", description = "Admin operations on videos (v1)")
@SecurityRequirement(name = "App Bearer Token")
class AdminVideoController(
    private val addVideoUseCase: AddVideoUseCase,
) {
    @PostMapping("/add")
    @Operation(
        summary = "Add a new video",
        description = "Version 1 of the API",
        tags = ["Admin Video API v1"]
    )
    @ApiResponses(
        ApiResponse(
            responseCode = "503",
            description = "Platform API Unavailable",
            content = [
                Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = ApiErrorDto::class)
                )
            ]
        ),
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
            responseCode = "403",
            description = "User not admin",
            content = [
                Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = ApiErrorDto::class)
                )
            ]
        ),
        ApiResponse(
            responseCode = "401",
            description = "Invalid App Bearer Token",
            content = [
                Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = ApiErrorDto::class)
                )
            ]
        ),
        ApiResponse(
            responseCode = "400",
            description = "Invalid video ref",
            content = [
                Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = ApiErrorDto::class)
                )
            ]
        ),
        ApiResponse(
            responseCode = "200",
            description = "Successfully added video",
            content = []
        )
    )
    suspend fun addVideo(@RequestBody videoRef: VideoRef) = addVideoUseCase.execute(videoRef)
}