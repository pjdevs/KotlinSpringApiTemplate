package org.example.demo.domain

import org.example.demo.domain.services.RandomSentenceService
import org.example.demo.domain.services.SentenceService
import kotlin.test.Test
import kotlin.test.assertTrue

class RandomSentenceServiceTests {
    private val sentenceService: SentenceService = RandomSentenceService()

    @Test
    fun serviceReturnsNonNullSentences() {
        for (i in 1..100) {
            assertTrue("Sentences should not be blank") { sentenceService.getSentence().isNotBlank() }
        }
    }
}