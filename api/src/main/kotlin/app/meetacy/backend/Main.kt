package app.meetacy.backend

import app.meetacy.backend.infrastructure.startMockEndpoints

fun main() {
    val port = System.getenv("PORT")?.toIntOrNull() ?: 8080

    startMockEndpoints(port, wait = true)
}
