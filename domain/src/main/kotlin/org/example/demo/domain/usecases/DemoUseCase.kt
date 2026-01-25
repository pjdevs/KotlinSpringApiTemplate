package org.example.demo.domain.usecases

import org.example.demo.domain.services.SentenceService

class DemoUseCase(private val sentenceService: SentenceService) {
    suspend fun sayHello(name: String?): String {
        val realName = name ?: "mysterious user"
        val sentence = sentenceService.getSentence()
        return "Hello $realName! This a cool sentence '${sentence.sentence}'."
    }
}