package org.example.demo.domain.services

import org.example.demo.domain.models.Sentence
import org.example.demo.domain.models.SentenceId
import java.util.UUID

class RandomSentenceService : SentenceService {
    override suspend fun getSentence(): Sentence = arrayOf(
        Sentence(SentenceId(UUID.randomUUID()), "Easy pizzy...", "easy"),
        Sentence(SentenceId(UUID.randomUUID()), "Look back at it!", "medium"),
        Sentence(SentenceId(UUID.randomUUID()), "I am a hero.", "hard")
    ).random()
}