package org.example.demo.api.auth

import org.example.demo.domain.ports.PasswordHasher
import org.springframework.security.crypto.password.PasswordEncoder

class SecurityPasswordHasher(private val encoder: PasswordEncoder) : PasswordHasher {
    override fun hash(password: String): String =
        encoder.encode(password) ?: throw RuntimeException("Password could not be encoded by $encoder")

    override fun isPasswordValid(password: String, passwordHash: String): Boolean =
        encoder.matches(password, passwordHash)
}