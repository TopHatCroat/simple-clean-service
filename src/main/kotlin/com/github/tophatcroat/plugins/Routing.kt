package com.github.tophatcroat.plugins

import io.ktor.routing.*
import io.ktor.http.*
import io.ktor.features.*
import io.ktor.locations.*
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.request.*

@OptIn(KtorExperimentalLocationsAPI::class)
fun Application.configureRouting() {
    
    install(Locations) {
    }

    routing {
        get("/") {
            call.respondText("Hello World!")
        }
        install(StatusPages) {
            exception<AuthenticationException> { cause ->
                call.respond(HttpStatusCode.Unauthorized)
            }
            exception<AuthorizationException> { cause ->
                call.respond(HttpStatusCode.Forbidden)
            }
        
        }
        get<MyLocation> {
                call.respondText("Location: name=${it.name}, arg1=${it.arg1}, arg2=${it.arg2}")
            }
            // Register nested routes
            get<Type.Edit> {
                call.respondText("Inside $it")
            }
            get<Type.List> {
                call.respondText("Inside $it")
            }
    }
}

class AuthenticationException : RuntimeException()

class AuthorizationException : RuntimeException()

@OptIn(KtorExperimentalLocationsAPI::class)
@Location("/location/{name}")
data class MyLocation(val name: String, val arg1: Int = 42, val arg2: String = "default")

@OptIn(KtorExperimentalLocationsAPI::class)
@Location("/type/{name}")
data class Type(val name: String) {
    @Location("/edit")
    data class Edit(val type: Type)

    @Location("/list/{page}")
    data class List(val type: Type, val page: Int)
}
