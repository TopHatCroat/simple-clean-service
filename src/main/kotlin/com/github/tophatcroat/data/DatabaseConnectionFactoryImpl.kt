package com.github.tophatcroat.data

import com.github.tophatcroat.config.AppConfig
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.jetbrains.exposed.sql.Database

class DatabaseConnectionFactoryImpl(appConfig: AppConfig) : DatabaseConnectionFactory {

    val dbConfig = appConfig.databaseConfig

    override fun connect() {
        Database.connect(hikari())
    }

    override fun disconnect() {
        TODO("Not yet implemented")
    }

    private fun hikari(): HikariDataSource {
        val config = HikariConfig()
        config.driverClassName = "org.postgresql.Driver"
        config.jdbcUrl = dbConfig.url
        config.username = dbConfig.username
        config.password = dbConfig.password
        config.maximumPoolSize = dbConfig.maxPoolSize
        config.isAutoCommit = false
        config.transactionIsolation = "TRANSACTION_REPEATABLE_READ"
        config.initializationFailTimeout = 10000

        config.validate()
        return HikariDataSource(config)
    }
}
