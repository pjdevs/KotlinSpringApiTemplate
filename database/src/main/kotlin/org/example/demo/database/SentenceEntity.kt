package org.example.demo.database

import org.example.demo.domain.models.Sentence;
import jakarta.persistence.Entity
import jakarta.persistence.Id
import org.example.demo.domain.models.SentenceId
import java.util.UUID

@Entity
class SentenceEntity(
    @Id
    val id: UUID,
    val sentence: String,
    val difficulty: String,
) {
    constructor() : this(UUID.randomUUID(), "", "")
    constructor(sentence: String, difficulty: String) : this(UUID.randomUUID(), sentence, difficulty)

    fun toDomain() = Sentence(SentenceId(id), sentence, difficulty)
}