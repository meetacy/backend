package app.meetacy.backend.application.endpoints

import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json

@OptIn(ExperimentalSerializationApi::class)
fun Application.installJson() {
    install(ContentNegotiation) {
        json(
            Json {
                explicitNulls = false
            }
        )
    }
}
