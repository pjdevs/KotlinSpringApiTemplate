package org.example.demo.database

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.example.demo.domain.models.Sentence
import org.example.demo.domain.ports.SentenceRepository

class JpaSentenceRepository(private val jpaRepository: SentenceJpaRepository) : SentenceRepository {
    override suspend fun getAllSentences(): Array<Sentence> =
        withContext(Dispatchers.IO) {
            jpaRepository.findAll().map { it.toDomain() }.toTypedArray()
        }
}