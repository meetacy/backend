package app.meetacy.backend.core.endpoints

import app.meetacy.backend.types.serializable.serialization
import io.ktor.server.application.*

fun ApplicationCall.latitude(): Double? = serialization {
    parameters["latitude"]?.toDouble()
}

fun ApplicationCall.longitude(): Double? = serialization {
    parameters["longitude"]?.toDouble()
}
