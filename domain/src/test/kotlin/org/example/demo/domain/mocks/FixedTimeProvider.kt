package org.example.demo.domain.mocks

import org.example.demo.domain.ports.TimeProvider
import java.time.LocalDate
import java.time.ZoneId
import kotlin.time.Instant
import kotlin.time.toJavaInstant

class FixedTimeProvider(var currentTime: Instant, var zone: ZoneId) : TimeProvider {
    override fun now(): Instant = currentTime

    override fun today(): LocalDate = LocalDate.ofInstant(currentTime.toJavaInstant(), zone)
}