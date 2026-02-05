package org.example.demo.infra.persistence

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.example.demo.domain.models.User
import org.example.demo.domain.models.UserRole
import org.example.demo.domain.utils.parseRoleSet
import org.example.demo.domain.utils.toRoleString

@Entity
@Table(name = "user")
class UserEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    val id: Int? = null,
    @Column(name = "username", nullable = false, unique = true)
    val userName: String = "",
    @Column(name = "password_hash", nullable = false)
    val passwordHash: String = "",
    @Column(name = "roles", nullable = false)
    val roles: String = "",
) {
    fun toDomain(): User {
        try {
            return User(
                userName,
                passwordHash,
                roles.parseRoleSet()
            )
        }
        catch (e: IllegalArgumentException) {
            throw InvalidDatabaseDataException("User roles could not be parsed from database for user $id ($userName): ${e.message}")
        }
    }
}

fun User.toEntity() : UserEntity = UserEntity(
    id = null,
    userName = userName,
    passwordHash = passwordHash,
    roles = roles.toRoleString(),
)
