package com.github.tophatcroat.feature.todo.integration

import com.github.tophatcroat.feature.todo.controller.TodoController
import com.github.tophatcroat.feature.todo.data.dao.TodoEntity
import com.github.tophatcroat.feature.todo.domain.model.TodoDto
import com.github.tophatcroat.withTestServer
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.locations.KtorExperimentalLocationsAPI
import io.ktor.locations.locations
import io.ktor.server.testing.handleRequest
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.Test
import org.koin.test.junit5.AutoCloseKoinTest
import strikt.api.expectThat
import strikt.assertions.hasSize
import strikt.assertions.isA
import strikt.assertions.isEqualTo
import java.util.UUID

@OptIn(KtorExperimentalLocationsAPI::class)
class TodoControllerTest : AutoCloseKoinTest() {

    @Test
    fun `get todo by ID works`() = withTestServer {

        val expected = transaction {
            TodoEntity.new(UUID.randomUUID()) {
                text = "sample"
            }
        }

        val href = application.locations.href(
            TodoController.Get(id = expected.id.value)
        )

        handleRequest(HttpMethod.Get, href).apply {
            expectThat(response.status()).isEqualTo(HttpStatusCode.OK)

            val res = Json.decodeFromString<TodoDto>(response.content!!)

            expectThat(res.id).isEqualTo(expected.id.value)
            expectThat(res.text).isEqualTo(expected.text)
        }
    }

    @Test
    fun `get all todo works`() = withTestServer {

        transaction {
            TodoEntity.new(UUID.randomUUID()) {
                text = "sample1"
            }
            TodoEntity.new(UUID.randomUUID()) {
                text = "sample2"
            }
        }

        val href = application.locations.href(
            TodoController.GetAll()
        )

        handleRequest(HttpMethod.Get, href).apply {
            expectThat(response.status()).isEqualTo(HttpStatusCode.OK)

            val res = Json.decodeFromString<List<TodoDto>>(response.content!!)

            expectThat(res).hasSize(2)
        }
    }

    @Test
    fun `create samples works`() = withTestServer {

        val href = application.locations.href(
            TodoController.Create(text = "test")
        )

        handleRequest(HttpMethod.Post, href).apply {
            expectThat(response.status()).isEqualTo(HttpStatusCode.Created)

            val res = Json.decodeFromString<TodoDto>(response.content!!)

            expectThat(res.id).isA<UUID>()
            expectThat(res.text).isEqualTo("test")
        }
    }
}
