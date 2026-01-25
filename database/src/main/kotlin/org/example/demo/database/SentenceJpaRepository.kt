package org.example.demo.database

import org.springframework.data.repository.CrudRepository
import java.util.UUID

interface SentenceJpaRepository : CrudRepository<SentenceEntity, UUID>