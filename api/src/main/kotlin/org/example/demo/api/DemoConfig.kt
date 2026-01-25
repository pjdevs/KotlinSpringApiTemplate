package org.example.demo.api

import org.example.demo.database.JpaSentenceRepository
import org.example.demo.database.SentenceJpaRepository
import org.example.demo.domain.ports.SentenceRepository
import org.example.demo.domain.services.DatabaseSentenceService
import org.example.demo.domain.services.RandomSentenceService
import org.example.demo.domain.services.SentenceService
import org.example.demo.domain.usecases.DemoUseCase
import org.springframework.boot.persistence.autoconfigure.EntityScan
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@Configuration
class DemoConfig {
    @Bean
    fun sentenceRepository(jpaRepository: SentenceJpaRepository) : SentenceRepository = JpaSentenceRepository(jpaRepository)
    @Bean
    fun sentenceService(sentenceRepository: SentenceRepository) : SentenceService = DatabaseSentenceService(sentenceRepository)
    @Bean
    fun demoUseCase(sentenceService: SentenceService) : DemoUseCase = DemoUseCase(sentenceService)
}