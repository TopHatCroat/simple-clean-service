package com.github.tophatcroat.feature.todo.controller

import com.github.tophatcroat.feature.todo.domain.TodoService
import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.locations.KtorExperimentalLocationsAPI
import io.ktor.locations.Location
import io.ktor.locations.get
import io.ktor.locations.post
import io.ktor.response.respond
import io.ktor.routing.Route
import org.koin.ktor.ext.inject
import java.util.UUID

@OptIn(KtorExperimentalLocationsAPI::class)
@Location("/sample")
class TodoRoute {

    @Location("")
    class GetAll

    @Location("")
    class Create(val text: String)

    @Location("/{id}")
    class Get(val id: UUID)
}

@OptIn(KtorExperimentalLocationsAPI::class)
fun Route.todoController() {

    val todoService by inject<TodoService>()

    get<TodoRoute.GetAll> {
        val result = todoService.getAll()

        call.respond(result.map { it })
    }

    get<TodoRoute.Get> { params ->
        val result = todoService.getOne(params.id)
        call.respond(result)
    }

    post<TodoRoute.Create> { params ->
        val result = todoService.createNew(params.text)

        call.respond(HttpStatusCode.Created, result)
    }
}
