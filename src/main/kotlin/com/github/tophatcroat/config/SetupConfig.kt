package com.github.tophatcroat.config

import io.ktor.application.Application
import org.koin.ktor.ext.inject

fun Application.setupConfig() {
    val appConfig by inject<AppConfig>()

    // Server
    val serverObject = environment.config.config("ktor.server")
    val isProd = serverObject.property("isProd").getString().toBoolean()
    appConfig.serverConfig = ServerConfig(isProd)

    // Database
    val databaseObject = environment.config.config("ktor.database")
    val url = databaseObject.property("url").getString()
    val username = databaseObject.property("username").getString()
    val password = databaseObject.property("password").getString()
    val maxPoolSize = databaseObject.property("maxPoolSize").getString().toInt()
    appConfig.databaseConfig = DatabaseConfig(
        url = url,
        username = username,
        password = password,
        maxPoolSize = maxPoolSize
    )
}
