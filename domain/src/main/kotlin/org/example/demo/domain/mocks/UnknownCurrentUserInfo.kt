package org.example.demo.domain.mocks

import org.example.demo.domain.ports.CurrentUserInfo

class UnknownCurrentUserInfo : CurrentUserInfo {
    override fun getUserName(): String = "unknownUser"
}