package org.example.demo.infra.auth

import org.example.demo.domain.ports.CurrentUserInfo

class UnknownCurrentUserInfo : CurrentUserInfo {
    override suspend fun getUserName(): String = "unknownUser"
}