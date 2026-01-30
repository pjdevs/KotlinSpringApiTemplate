package org.example.demo.domain.ports

interface CurrentUserInfo {
    suspend fun getUserName(): String
}