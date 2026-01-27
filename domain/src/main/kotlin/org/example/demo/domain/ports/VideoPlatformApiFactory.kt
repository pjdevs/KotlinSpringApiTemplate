package org.example.demo.domain.ports

import org.example.demo.domain.models.VideoPlatform

interface VideoPlatformApiFactory {
    fun create(platform: VideoPlatform) : VideoPlatformApi
}