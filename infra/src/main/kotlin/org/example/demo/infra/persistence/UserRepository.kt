package org.example.demo.infra.persistence

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.example.demo.domain.models.User
import org.example.demo.domain.ports.UserRepository
import org.springframework.data.repository.CrudRepository

interface UserJpaRepository : CrudRepository<UserEntity, Int> {
    fun findFirstByUserName(userName: String): UserEntity?
}

class JpaUserRepository(private val jpaRepository: UserJpaRepository) : UserRepository {
    override suspend fun getUserByName(userName: String): User? =
        withContext(Dispatchers.IO) {
            jpaRepository.findFirstByUserName(userName)?.toDomain()
        }
}
