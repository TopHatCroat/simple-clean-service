package com.github.tophatcroat.feature.todo.domain

import com.github.tophatcroat.feature.todo.domain.model.TodoDto
import java.util.UUID

interface TodoService {
    suspend fun createNew(text: String): TodoDto

    suspend fun getOne(id: UUID): TodoDto

    suspend fun getAll(): Iterable<TodoDto>
}

