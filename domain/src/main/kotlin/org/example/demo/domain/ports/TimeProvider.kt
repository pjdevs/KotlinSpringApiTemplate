package org.example.demo.domain.ports

import java.time.LocalDate
import kotlin.time.Instant

interface TimeProvider {
    fun now(): Instant
    fun today(): LocalDate
}