package com.github.tophatcroat

import appModule
import com.github.tophatcroat.config.setupConfig
import com.github.tophatcroat.data.DatabaseConnectionFactory
import com.github.tophatcroat.feature.todo.controller.todoController
import com.github.tophatcroat.plugins.configureRouting
import com.github.tophatcroat.plugins.configureSecurity
import io.ktor.application.*
import io.ktor.features.CORS
import io.ktor.features.CallLogging
import io.ktor.features.ContentNegotiation
import io.ktor.features.DataConversion
import io.ktor.features.DefaultHeaders
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.request.path
import io.ktor.routing.routing
import io.ktor.serialization.json
import io.ktor.util.DataConversionException
import java.util.UUID
import org.koin.ktor.ext.Koin
import org.koin.logger.slf4jLogger
import org.koin.core.module.Module
import org.koin.ktor.ext.inject
import org.slf4j.event.Level

fun main(args: Array<String>): Unit =
    io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // application.conf references the main function. This annotation prevents the IDE from marking it as unused.
fun Application.module(koinModules: List<Module> = listOf(appModule)) {

    install(Koin) {
        slf4jLogger()
        modules(koinModules)
    }

    setupConfig()

    val databaseConnectionFactory by inject<DatabaseConnectionFactory>()
    databaseConnectionFactory.connect()

    // configure serialization
    install(ContentNegotiation) {
        json()
    }

    install(DataConversion) {
        convert<UUID> {
            decode { values, _ ->
                values.firstOrNull()?.let { UUID.fromString(it) }
            }
            encode { value ->
                when (value) {
                    null -> listOf()
                    is UUID -> listOf(value.toString())
                    else -> throw DataConversionException("Cannot convert $value as Date")
                }
            }
        }
    }

    // configure monitoring
    install(CallLogging) {
        level = Level.INFO
        filter { call -> call.request.path().startsWith("/") }
    }

    // configure HTTP
    install(DefaultHeaders) {
        header("X-Engine", "Ktor") // will send this header with each response
    }
    install(CORS) {
        method(HttpMethod.Options)
        method(HttpMethod.Put)
        method(HttpMethod.Delete)
        method(HttpMethod.Patch)
        header(HttpHeaders.Authorization)
        header("MyCustomHeader")
        anyHost() // @TODO: Don't do this in production if possible. Try to limit it.
    }

    configureSecurity()

    configureRouting()

    routing {
        todoController()
    }
}
