package org.example.demo.domain.ports

import org.example.demo.domain.models.User

interface UserRepository {
    suspend fun getUserByName(userName: String): User?
}