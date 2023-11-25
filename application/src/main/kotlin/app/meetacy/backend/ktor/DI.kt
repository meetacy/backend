package app.meetacy.backend.ktor

import app.meetacy.backend.ktor.client.clientEngine
import app.meetacy.backend.ktor.client.httpClient
import app.meetacy.di.builder.DIBuilder

fun DIBuilder.ktor() {
    clientEngine()
    httpClient()
}
