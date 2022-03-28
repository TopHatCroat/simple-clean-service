package com.github.tophatcroat.feature.todo.data

import com.github.tophatcroat.feature.todo.data.dao.TodoEntity
import com.github.tophatcroat.feature.todo.data.dao.TodoTable
import com.github.tophatcroat.feature.todo.data.mapper.toDto
import com.github.tophatcroat.feature.todo.domain.model.TodoDto
import com.github.tophatcroat.feature.todo.domain.TodoCreateDto
import com.github.tophatcroat.feature.todo.domain.TodoDataSource
import java.util.UUID
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

class TodoDataSourceImpl : TodoDataSource {
    override suspend fun create(data: TodoCreateDto): TodoDto {
        return transaction { TodoEntity.new { text = data.text } }.toDto()
    }

    override suspend fun getById(id: UUID): TodoDto {
        val query = TodoTable.select { TodoTable.id eq id }

        return transaction { TodoEntity.wrapRow(query.single()) }.toDto()
    }

    override suspend fun getAll(): List<TodoDto> {
        val query = TodoTable.selectAll()

        val entities = transaction { TodoEntity.wrapRows(query).toList() }

        return entities.map { it.toDto() }
    }
}