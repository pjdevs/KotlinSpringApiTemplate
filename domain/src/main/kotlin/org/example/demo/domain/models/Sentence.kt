package org.example.demo.domain.models

import java.util.UUID

@JvmInline
value class SentenceId(val id: UUID)

class Sentence(val id: SentenceId, val sentence: String, val difficulty: String)