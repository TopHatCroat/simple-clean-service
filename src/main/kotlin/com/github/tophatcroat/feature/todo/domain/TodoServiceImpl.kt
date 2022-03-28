package com.github.tophatcroat.feature.todo.domain

import com.github.tophatcroat.feature.todo.domain.model.TodoDto
import java.util.UUID

class TodoServiceImpl(
    val todoDataSource: TodoDataSource
) : TodoService {
    override suspend fun createNew(text: String): TodoDto {
        return todoDataSource.create(TodoCreateDto(text))
    }

    override suspend fun getOne(id: UUID): TodoDto {
        return todoDataSource.getById(id)
    }

    override suspend fun getAll(): Iterable<TodoDto> {
        return todoDataSource.getAll()
    }
}
