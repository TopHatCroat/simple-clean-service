package com.github.tophatcroat.feature.todo.data.mapper

import com.github.tophatcroat.feature.todo.data.dao.TodoEntity
import com.github.tophatcroat.feature.todo.domain.model.TodoDto

fun TodoEntity.toDto(): TodoDto = TodoDto(
    id = this.id.value,
    text = this.text
)
