package org.example.demo.api.auth

import kotlinx.coroutines.reactive.awaitSingle
import org.example.demo.domain.ports.CurrentUserInfo
import org.springframework.security.core.context.ReactiveSecurityContextHolder

class SecurityCurrentUserInfo : CurrentUserInfo {
    override suspend fun getUserName(): String {
        return ReactiveSecurityContextHolder.getContext()
            .map { it.authentication?.name ?: "anonymous" }
            .awaitSingle()
    }
}