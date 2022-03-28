package com.github.tophatcroat.feature.todo.controller

import com.apurebase.kgraphql.schema.dsl.SchemaBuilder
import com.github.tophatcroat.di.inject
import com.github.tophatcroat.feature.todo.domain.TodoService
import com.github.tophatcroat.feature.todo.domain.model.TodoDto
import java.util.UUID

fun SchemaBuilder.todoQlController() {

    val todoService by inject<TodoService>()

    type<TodoDto>()

    stringScalar<UUID> {
        serialize = UUID::toString
        deserialize = UUID::fromString
    }

    query("todo") {
        resolver { id: UUID ->
            todoService.getOne(id)
        }
    }

    query("todos") {
        resolver<Iterable<TodoDto>> {
            todoService.getAll()
        }
    }

    mutation("createTodo") {
        resolver { text: String ->
            todoService.createNew(text)
        }
    }
}