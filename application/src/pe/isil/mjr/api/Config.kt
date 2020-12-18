package pe.isil.mjr.api

import io.ktor.config.*
import io.ktor.util.*

@KtorExperimentalAPI
class Config(config: ApplicationConfig) {
    val key = config.property("key.secret").getString()

    val host = config.property("database.host").getString()
    val port = config.property("database.port").getString()
    val name = config.property("database.name").getString()
    val user = config.property("database.user").getString()
    val password = config.property("database.password").getString()
}