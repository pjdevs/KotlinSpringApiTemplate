package org.example.demo.domain.models

enum class UserRole {
    USER,
    ADMIN,
}

class User(val userName: String, val passwordHash: String, val roles: Set<UserRole>)

class AuthenticatedUser(val userName: String, val roles: Set<UserRole>)