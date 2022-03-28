package com.github.tophatcroat.data

import com.github.tophatcroat.config.AppConfig
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.jetbrains.exposed.sql.Database

class IntegrationTestDatabaseConnectionFactoryImpl(appConfig: AppConfig) : DatabaseConnectionFactory {

    private val dbConfig = appConfig.databaseConfig

    override fun connect() {
        Database.connect(hikari())

        SchemaDefinition.createSchema()

        SchemaDefinition.clean()
    }

    override fun disconnect() {
        // no-op
    }

    private fun hikari(): HikariDataSource {
        val config = HikariConfig()
        config.driverClassName = "org.postgresql.Driver"
        config.jdbcUrl = dbConfig.url
        config.username = dbConfig.username
        config.password = dbConfig.password
        config.maximumPoolSize = dbConfig.maxPoolSize
        config.isAutoCommit = true
        config.validate()
        return HikariDataSource(config)
    }
}
