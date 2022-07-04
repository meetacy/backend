package app.meetacy.backend

import app.meetacy.backend.endpoint.startEndpoints

fun main() {
    val port = System.getenv("PORT")?.toIntOrNull() ?: 8080

    startEndpoints(port, wait = true)
}
