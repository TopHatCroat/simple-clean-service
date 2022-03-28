package com.github.tophatcroat.feature.todo.domain

import com.github.tophatcroat.feature.todo.domain.model.TodoDto
import java.util.UUID

interface TodoDataSource {
    suspend fun create(data: TodoCreateDto): TodoDto

    suspend fun getById(id: UUID): TodoDto

    suspend fun getAll(): List<TodoDto>
}

class TodoCreateDto(
    val text: String
)
