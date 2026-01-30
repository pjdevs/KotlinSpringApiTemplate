package org.example.demo.api.errors

import io.swagger.v3.oas.annotations.Hidden
import org.example.demo.domain.dtos.ApiErrorDto
import org.example.demo.domain.exceptions.InvalidReactionException
import org.example.demo.domain.exceptions.InvalidStateException
import org.example.demo.domain.exceptions.InvalidVideoRefException
import org.example.demo.domain.exceptions.VideoNotFoundException
import org.example.demo.domain.exceptions.VideoPlatformApiException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.reactive.resource.NoResourceFoundException
import org.springframework.web.server.MissingRequestValueException

@RestControllerAdvice
@Hidden
class GlobalErrorHandler {

    @ExceptionHandler(InvalidStateException::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun handleException(e: InvalidStateException) = ApiErrorDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.message)

    @ExceptionHandler(VideoNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleException(e: VideoNotFoundException) = ApiErrorDto(HttpStatus.NOT_FOUND.value(), e.message)

    @ExceptionHandler(InvalidVideoRefException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleException(e: InvalidVideoRefException) = ApiErrorDto(HttpStatus.BAD_REQUEST.value(), e.message)

    @ExceptionHandler(InvalidReactionException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleException(e: InvalidReactionException) = ApiErrorDto(HttpStatus.BAD_REQUEST.value(), e.message)

    @ExceptionHandler(VideoPlatformApiException::class)
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    fun handleException(e: VideoPlatformApiException) = ApiErrorDto(HttpStatus.SERVICE_UNAVAILABLE.value(), e.message)

    @ExceptionHandler(IllegalArgumentException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleBadRequest(e: IllegalArgumentException) = ApiErrorDto(HttpStatus.BAD_REQUEST.value(), e.message)

    @ExceptionHandler(MissingRequestValueException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleBadRequest(e: MissingRequestValueException) = ApiErrorDto(HttpStatus.BAD_REQUEST.value(), e.message)

    @ExceptionHandler(NoResourceFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleNoResource(e: NoResourceFoundException) = ApiErrorDto(HttpStatus.NOT_FOUND.value(), e.message)

    @ExceptionHandler(Exception::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun handleGeneric(e: Exception): ApiErrorDto {
        e.printStackTrace()
        return ApiErrorDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), "An unexpected error occurred")
    }

}