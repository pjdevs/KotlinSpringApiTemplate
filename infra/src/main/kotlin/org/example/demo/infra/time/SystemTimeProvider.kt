package org.example.demo.infra.time

import org.example.demo.domain.ports.TimeProvider
import java.time.LocalDate
import kotlin.time.Clock
import kotlin.time.Instant

class SystemTimeProvider : TimeProvider {
    override fun now(): Instant = Clock.System.now()
    override fun today(): LocalDate = LocalDate.now()
}