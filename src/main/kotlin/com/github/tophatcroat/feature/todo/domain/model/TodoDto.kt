package com.github.tophatcroat.feature.todo.domain.model

import com.github.tophatcroat.serialization.UuidSerializer
import java.util.UUID
import kotlinx.serialization.Serializable

@Serializable
data class TodoDto(
    @Serializable(UuidSerializer::class)
    val id: UUID,
    val text: String
)