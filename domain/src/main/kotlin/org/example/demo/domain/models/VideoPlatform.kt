package org.example.demo.domain.models

import org.example.demo.domain.exceptions.InvalidVideoRefException

enum class VideoPlatform(val platformName: String) {
    YOUTUBE("youtube"),
    ;

    companion object {
        fun fromPlatformName(str: String): VideoPlatform =
            entries.firstOrNull { it.platformName == str }
                ?: throw InvalidVideoRefException("Invalid or unsupported platform $str")
    }

    override fun toString(): String = platformName
}