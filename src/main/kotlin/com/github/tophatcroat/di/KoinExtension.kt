package com.github.tophatcroat.di

import com.apurebase.kgraphql.schema.dsl.SchemaBuilder
import org.koin.core.context.GlobalContext
import org.koin.core.parameter.ParametersDefinition
import org.koin.core.qualifier.Qualifier

/**
 * Ktor Koin extensions for Routing class
 *
 * @author Arnaud Giuliani
 * @author Laurent Baresse
 */

/**
 * inject lazily given dependency
 * @param qualifier - bean name / optional
 * @param parameters
 */
inline fun <reified T : Any> SchemaBuilder.inject(
    qualifier: Qualifier? = null,
    noinline parameters: ParametersDefinition? = null
) =
    lazy { get<T>(qualifier, parameters) }

/**
 * Retrieve given dependency for KoinComponent
 * @param qualifier - bean name / optional
 * @param parameters
 */
inline fun <reified T : Any> SchemaBuilder.get(
    qualifier: Qualifier? = null,
    noinline parameters: ParametersDefinition? = null
) =
    getKoin().get<T>(qualifier, parameters)

/**
 * Retrieve given property for KoinComponent
 * @param key - key property
 */
fun <T : Any> SchemaBuilder.getProperty(key: String) =
    getKoin().getProperty<T>(key)

/**
 * Retrieve given property for KoinComponent
 * give a default value if property is missing
 *
 * @param key - key property
 * @param defaultValue - default value if property is missing
 *
 */
fun SchemaBuilder.getProperty(key: String, defaultValue: String) =
    getKoin().getProperty(key) ?: defaultValue

/**
 * Help work on ModuleDefinition
 */
fun SchemaBuilder.getKoin() = GlobalContext.get()
