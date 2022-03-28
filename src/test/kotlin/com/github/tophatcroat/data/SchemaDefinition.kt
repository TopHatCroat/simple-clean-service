package com.github.tophatcroat.data

import com.github.tophatcroat.feature.todo.data.dao.TodoTable
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.deleteAll
import org.jetbrains.exposed.sql.transactions.transaction

object SchemaDefinition {
    fun createSchema() {
        transaction {
            SchemaUtils.create(TodoTable)
        }
    }

    fun clean() {
        transaction {
            TodoTable.deleteAll()
        }
    }
}