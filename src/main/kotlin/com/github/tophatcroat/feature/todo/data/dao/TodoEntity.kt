package com.github.tophatcroat.feature.todo.data.dao

import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.javatime.datetime
import java.time.LocalDateTime
import java.util.UUID

object TodoTable : IdTable<UUID>(name = "sample") {
    val createdAt = datetime("created_at").default(LocalDateTime.now())
    val updatedAt = datetime("updated_at").default(LocalDateTime.now())
    val text = text("text")
    val done = bool("done").default(false)

    override val id: Column<EntityID<UUID>> = uuid("id").entityId().default(EntityID(UUID.randomUUID(), TodoTable))
    override val primaryKey: PrimaryKey = PrimaryKey(id)
}

class TodoEntity(id: EntityID<UUID>) : Entity<UUID>(id) {
    companion object : EntityClass<UUID, TodoEntity>(TodoTable)

    var createdAt by TodoTable.createdAt
    var updatedAt by TodoTable.updatedAt
    var text by TodoTable.text
    var done by TodoTable.done
}
