package com.github.tophatcroat.feature.todo.domain.model

import com.github.tophatcroat.serialization.UUIDSerializer
import java.util.UUID
import kotlinx.serialization.Serializable

@Serializable
data class TodoDto(
    @Serializable(UUIDSerializer::class)
    val id: UUID,
    val text: String
)