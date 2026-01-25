package org.example.demo.domain.services

import org.example.demo.domain.ports.SentenceRepository

class DatabaseSentenceService(private val repository: SentenceRepository) : SentenceService {
    override suspend fun getSentence() = repository.getAllSentences().random()
}