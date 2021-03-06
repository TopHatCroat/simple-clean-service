val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project
val exposed_version: String by project
val hikaricp_version: String by project
val postgres_driver_version: String by project
val h2_version: String by project
val koin_version: String by project
val junit_version: String by project
val test_containers_version: String by project
val mockk_version: String by project
val strikt_version: String by project
val kgraphql_version: String by project
val detekt_version: String by project

plugins {
    application
    kotlin("jvm") version "1.6.10"
    id("org.jetbrains.kotlin.plugin.serialization") version "1.6.10"

    id("io.gitlab.arturbosch.detekt") version "1.20.0-RC1"

    id("com.github.johnrengelman.shadow") version "7.1.2"
}

group = "com.github.tophatcroat"
version = "0.0.1"

repositories {
    mavenCentral()
}

dependencies {
    detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:$detekt_version")

    implementation("io.ktor:ktor-server-core:$ktor_version")
    implementation("io.ktor:ktor-serialization:$ktor_version")
    implementation("io.ktor:ktor-server-host-common:$ktor_version")
    implementation("io.ktor:ktor-auth:$ktor_version")
    implementation("io.ktor:ktor-locations:$ktor_version")
    implementation("io.ktor:ktor-client-core:$ktor_version")
    implementation("io.ktor:ktor-client-apache:$ktor_version")
    implementation("io.ktor:ktor-server-netty:$ktor_version")

    implementation("ch.qos.logback:logback-classic:$logback_version")

    implementation("io.insert-koin:koin-ktor:$koin_version")
    implementation("io.insert-koin:koin-logger-slf4j:$koin_version")

    implementation("org.jetbrains.exposed:exposed-core:$exposed_version")
    implementation("org.jetbrains.exposed:exposed-dao:$exposed_version")
    implementation("org.jetbrains.exposed:exposed-jdbc:$exposed_version")
    implementation("org.jetbrains.exposed:exposed-java-time:$exposed_version")

    implementation("com.zaxxer:HikariCP:$hikaricp_version")
    implementation("org.postgresql:postgresql:$postgres_driver_version")

    testImplementation("org.junit.jupiter:junit-jupiter:$junit_version")
    testImplementation("io.ktor:ktor-server-tests:$ktor_version")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")
    testImplementation("io.mockk:mockk:$mockk_version")
    testImplementation("io.strikt:strikt-core:$strikt_version")

    implementation("com.apurebase:kgraphql:$kgraphql_version")
    implementation("com.apurebase:kgraphql-ktor:$kgraphql_version")

    testImplementation("io.insert-koin:koin-test:$koin_version")
    testImplementation("io.insert-koin:koin-test-junit5:$koin_version")

    testImplementation("org.testcontainers:testcontainers:$test_containers_version")
    testImplementation("org.testcontainers:junit-jupiter:$test_containers_version")
    testImplementation("org.testcontainers:postgresql:$test_containers_version")
}

// Prebuild config
detekt {
    config = files("$projectDir/detekt.yml")
}

tasks.withType<io.gitlab.arturbosch.detekt.Detekt>().configureEach {
    jvmTarget = "17"
}

tasks.withType<io.gitlab.arturbosch.detekt.DetektCreateBaselineTask>().configureEach {
    jvmTarget = "17"
}

// Compilation config
tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    kotlinOptions.freeCompilerArgs += "-opt-in=kotlin.RequiresOptIn"
    kotlinOptions.jvmTarget = "17"
}

// Test config
tasks.test {
    useJUnitPlatform()
}

// Run config
application {
    mainClass.set("io.ktor.server.netty.EngineMain")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

// Artifact config
tasks {
    shadowJar {
        manifest {
            attributes(Pair("Main-Class", "com.github.tophatcroat.ApplicationKt"))
            archiveFileName.set("app.jar")
        }
    }
}
