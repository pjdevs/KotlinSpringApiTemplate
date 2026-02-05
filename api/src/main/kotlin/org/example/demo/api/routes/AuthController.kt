package org.example.demo.api.routes

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.example.demo.domain.dtos.ApiErrorDto
import org.example.demo.domain.dtos.LoginRequestDto
import org.example.demo.domain.dtos.UserTokensDto
import org.example.demo.domain.usecases.AuthResult
import org.example.demo.domain.usecases.LoginUseCase
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/auth")
@Tag(name = "Auth API v1", description = "Authentication (v1)")
class AuthController(
    private val loginUseCase: LoginUseCase,
) {
    @PostMapping("/login")
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
            description = "User not found",
            content = [
                Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = ApiErrorDto::class)
                )
            ]
        ),
        ApiResponse(
            responseCode = "401",
            description = "Invalid password",
            content = [
                Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = ApiErrorDto::class)
                )
            ]
        ),
        ApiResponse(
            responseCode = "400",
            description = "Wrong JSON input",
            content = [
                Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = ApiErrorDto::class)
                )
            ]
        ),
        ApiResponse(
            responseCode = "200",
            description = "User logged in, tokens created",
            content = [
                Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = UserTokensDto::class)
                )
            ]
        )
    )
    suspend fun login(@RequestBody loginRequest: LoginRequestDto): ResponseEntity<Any> {
        val result = loginUseCase.execute(loginRequest)

        return when (result) {
            AuthResult.InvalidPassword -> ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(ApiErrorDto(
                    code = HttpStatus.UNAUTHORIZED.value(),
                    message = "Invalid password"
                ))
            AuthResult.UserNotFound -> ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ApiErrorDto(
                    code = HttpStatus.NOT_FOUND.value(),
                    message = "Invalid username"
                ))
            is AuthResult.Success -> ResponseEntity
                .status(HttpStatus.OK)
                .body(result.userTokensDto)
        }
    }
}