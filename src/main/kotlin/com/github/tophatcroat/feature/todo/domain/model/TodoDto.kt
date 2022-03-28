package com.github.tophatcroat.feature.todo.domain.model

import com.github.tophatcroat.serialization.UuidSerializer
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class TodoDto(
    @Serializable(UuidSerializer::class)
    val id: UUID,
    val text: String
)
