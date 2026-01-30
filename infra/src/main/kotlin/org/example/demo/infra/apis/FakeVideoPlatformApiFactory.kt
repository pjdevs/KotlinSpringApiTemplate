package org.example.demo.infra.apis

import org.example.demo.domain.models.VideoPlatform
import org.example.demo.domain.ports.VideoPlatformApi
import org.example.demo.domain.ports.VideoPlatformApiFactory

class FakeVideoPlatformApiFactory : VideoPlatformApiFactory {
    override fun create(platform: VideoPlatform): VideoPlatformApi = when (platform) {
        VideoPlatform.YOUTUBE -> FakeVideoPlatformApi(VideoPlatform.YOUTUBE)
    }
}