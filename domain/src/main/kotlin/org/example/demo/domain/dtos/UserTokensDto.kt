package org.example.demo.domain.dtos

import org.example.demo.domain.models.UserRole

data class UserTokensDto(
    val accessToken: String,
    val refreshToken: String,
    val expiresIn: Long,
    val roles: Set<UserRole>
)