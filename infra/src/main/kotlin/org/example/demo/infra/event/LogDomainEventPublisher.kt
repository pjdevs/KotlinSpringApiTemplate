package org.example.demo.infra.event

import org.example.demo.domain.ports.DomainEventPublisher
import org.slf4j.LoggerFactory

class LogDomainEventPublisher : DomainEventPublisher {

    private val logger = LoggerFactory.getLogger(LogDomainEventPublisher::class.java)

    override suspend fun publish(event: String) {
        // Can do an impl to publish to kafka, real persistent logs etc
        logger.info("Domain event published: '{}'.", event)
    }
}