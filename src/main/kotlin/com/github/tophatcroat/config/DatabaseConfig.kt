package com.github.tophatcroat.config

data class DatabaseConfig(
    val url: String,
    val username: String,
    val password: String,
    val maxPoolSize: Int
)
