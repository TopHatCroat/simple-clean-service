@file:Suppress("MatchingDeclarationName")
package com.github.tophatcroat

import com.github.tophatcroat.config.AppConfig
import com.github.tophatcroat.data.DatabaseConnectionFactory
import com.github.tophatcroat.data.IntegrationTestDatabaseConnectionFactoryImpl
import com.github.tophatcroat.feature.todo.data.TodoDataSourceImpl
import com.github.tophatcroat.feature.todo.domain.TodoDataSource
import com.github.tophatcroat.feature.todo.domain.TodoService
import com.github.tophatcroat.feature.todo.domain.TodoServiceImpl
import io.ktor.config.MapApplicationConfig
import io.ktor.locations.KtorExperimentalLocationsAPI
import io.ktor.server.testing.TestApplicationEngine
import io.ktor.server.testing.withTestApplication
import org.koin.core.annotation.KoinReflectAPI
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import org.testcontainers.containers.PostgreSQLContainer

object TestContainers {
    val pg = PostgreSQLContainer("postgres")

    fun isStarted() = pg.isCreated

    fun start() = pg.start()
}

fun MapApplicationConfig.createConfigForTesting() {
    // Server config
    put("ktor.server.isProd", "false")

    put("ktor.database.url", TestContainers.pg.jdbcUrl)
    put("ktor.database.username", TestContainers.pg.username)
    put("ktor.database.password", TestContainers.pg.password)
    put("ktor.database.maxPoolSize", "1")
}

@OptIn(KoinReflectAPI::class)
val appTestModule = module {
    singleOf(::AppConfig)
    singleOf(::IntegrationTestDatabaseConnectionFactoryImpl) bind DatabaseConnectionFactory::class

    singleOf(::TodoDataSourceImpl) bind TodoDataSource::class
    singleOf(::TodoServiceImpl) bind TodoService::class
}

@KtorExperimentalLocationsAPI
fun withTestServer(koinModules: List<Module> = listOf(appTestModule), test: TestApplicationEngine.() -> Unit) {
    if (!TestContainers.isStarted()) {
        TestContainers.start()
    }

    withTestApplication(
        {
            (environment.config as MapApplicationConfig).apply {
                createConfigForTesting()
            }

            module(koinModules = koinModules)
        }, test
    )
}
