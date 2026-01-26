package org.example.demo.domain.ports

interface DomainEventPublisher {
    suspend fun publish(event: String)
}