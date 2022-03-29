package com.github.tophatcroat.di

import com.github.tophatcroat.config.AppConfig
import com.github.tophatcroat.data.DatabaseConnectionFactory
import com.github.tophatcroat.data.DatabaseConnectionFactoryImpl
import com.github.tophatcroat.feature.todo.data.TodoDataSourceImpl
import com.github.tophatcroat.feature.todo.domain.TodoDataSource
import com.github.tophatcroat.feature.todo.domain.TodoService
import com.github.tophatcroat.feature.todo.domain.TodoServiceImpl
import org.koin.core.annotation.KoinReflectAPI
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

@OptIn(KoinReflectAPI::class)
val appModule = module {
    // Backend Config
    singleOf(::AppConfig)
    singleOf(::DatabaseConnectionFactoryImpl) bind DatabaseConnectionFactory::class
    singleOf(::TodoDataSourceImpl) bind TodoDataSource::class
    singleOf(::TodoServiceImpl) bind TodoService::class
}
