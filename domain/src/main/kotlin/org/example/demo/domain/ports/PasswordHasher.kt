package org.example.demo.domain.ports

interface PasswordHasher {
    fun hash(password: String): String
    fun isPasswordValid(password: String, passwordHash: String): Boolean
}