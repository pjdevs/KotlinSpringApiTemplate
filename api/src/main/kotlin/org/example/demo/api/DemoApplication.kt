package org.example.demo.api

import org.example.demo.database.SentenceEntity
import org.example.demo.database.SentenceJpaRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.persistence.autoconfigure.EntityScan
import org.springframework.context.annotation.Bean
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@SpringBootApplication
@EnableJpaRepositories(basePackages = ["org.example.demo"])
@EntityScan(basePackages = ["org.example.demo"])
class DemoApplication {

    @Bean
    fun init(repository: SentenceJpaRepository) = CommandLineRunner {
        repository.save(SentenceEntity("Easy pizzy...", "easy"))
        repository.save(SentenceEntity("Look back at it!", "medium"))
        repository.save(SentenceEntity("I am a hero.", "hard"))
    }

}