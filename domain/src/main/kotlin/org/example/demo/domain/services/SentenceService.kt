package org.example.demo.domain.services

import org.example.demo.domain.models.Sentence

interface SentenceService {
    suspend fun getSentence(): Sentence
}
