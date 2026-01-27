package org.example.demo.domain.models

import org.example.demo.domain.exceptions.InvalidVideoRefException

enum class VideoPlatform {
    YOUTUBE;

    companion object {
        fun fromPlatformName(str: String): VideoPlatform =
            try {
                VideoPlatform.valueOf(str.uppercase())
            } catch (e: IllegalArgumentException) {
                throw InvalidVideoRefException("Invalid or unsupported platform $str")
            }
    }
}