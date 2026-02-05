package org.example.demo.infra.persistence

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.example.demo.domain.models.DraftVideo
import org.example.demo.domain.models.User
import org.example.demo.domain.models.Video
import org.example.demo.domain.models.VideoRef
import org.example.demo.domain.ports.UserRepository
import org.example.demo.domain.ports.VideoRepository
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param

interface UserJpaRepository : CrudRepository<UserEntity, Int> {
    fun findFirstByUserName(userName: String): UserEntity?
}

class JpaUserRepository(private val jpaRepository: UserJpaRepository) : UserRepository {
    override suspend fun getUserByName(userName: String): User? =
        withContext(Dispatchers.IO) {
            jpaRepository.findFirstByUserName(userName)?.toDomain()
        }
}
