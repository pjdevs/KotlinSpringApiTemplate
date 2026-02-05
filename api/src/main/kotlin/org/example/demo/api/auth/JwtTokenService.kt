package org.example.demo.api.auth

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.example.demo.domain.models.AuthenticatedUser
import org.example.demo.domain.models.User
import org.example.demo.domain.ports.TokenService
import org.example.demo.domain.ports.UserTokens
import org.example.demo.domain.utils.parseRoleSet
import org.example.demo.domain.utils.toRoleString
import java.util.Date
import javax.crypto.SecretKey

class InvalidJwtTokenException(message: String) : RuntimeException(message)

class JwtTokenService(secret: String, private val expiration: Long) : TokenService {
    private val key: SecretKey = Keys.hmacShaKeyFor(secret.toByteArray())

    override fun generateToken(user: User): UserTokens {
        val now = Date()
        val expiry = now.time + expiration
        val expiryDate = Date(expiry)

        val token = Jwts.builder()
            .subject(user.userName)
            .claim("roles", user.roles.toRoleString())
            .issuedAt(now)
            .expiration(expiryDate)
            .signWith(key)
            .compact()

        return UserTokens(token, "none", expiry, user.roles)
    }

    override fun validateToken(token: String): AuthenticatedUser? {
        return try {
            val claims = Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .payload

            AuthenticatedUser(
                userName = claims.subject,
                roles = claims["roles"]
                    .let { it as? String? }
                    .let { it ?: throw IllegalArgumentException("missing roles in JWT payload") }
                    .parseRoleSet(),
            )
        } catch (e: Exception) {
            throw InvalidJwtTokenException("Invalid JWT token: ${e.message}")
        }
    }
}