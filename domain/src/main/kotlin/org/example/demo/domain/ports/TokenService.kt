package org.example.demo.domain.ports

import org.example.demo.domain.models.AuthenticatedUser
import org.example.demo.domain.models.User
import org.example.demo.domain.models.UserRole

data class UserTokens(
    val accessToken: String,
    val refreshToken: String,
    val expiresIn: Long,
    val roles: Set<UserRole>
)

interface TokenService {
    fun generateToken(user: User): UserTokens
    fun validateToken(token: String): AuthenticatedUser?
}