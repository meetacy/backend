package app.meetacy.backend.ktor.client

import app.meetacy.di.builder.DIBuilder
import io.ktor.client.*
import io.ktor.client.engine.*

fun DIBuilder.httpClient() {
    val httpClient by factory {
        val clientEngine: HttpClientEngine by getting
        HttpClient(clientEngine)
    }
}
