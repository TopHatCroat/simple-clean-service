package com.github.tophatcroat.feature.todo.unit

import com.github.tophatcroat.feature.todo.data.dao.TodoEntity
import com.github.tophatcroat.feature.todo.data.dao.TodoTable
import com.github.tophatcroat.feature.todo.data.mapper.toDto
import io.mockk.every
import io.mockk.mockk
import java.util.UUID
import org.jetbrains.exposed.dao.id.EntityID
import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class TodoMapperTest {

    @Test
    fun `sample mapper works`() {
        val uuid = UUID.randomUUID()
        val text = "text"

        val todoEntity = mockk<TodoEntity>()

        every { todoEntity.id } returns EntityID(uuid, TodoTable)
        every { todoEntity.text } returns text

        val actual = todoEntity.toDto()

        expectThat(uuid).isEqualTo(actual.id)
        expectThat(text).isEqualTo(actual.text)
    }
}