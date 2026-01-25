package org.example.demo.domain.ports

import org.example.demo.domain.models.Sentence

interface SentenceRepository {
    suspend fun getAllSentences() : Array<Sentence>
}