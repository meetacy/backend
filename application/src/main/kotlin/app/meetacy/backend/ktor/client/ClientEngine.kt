package app.meetacy.backend.ktor.client

import app.meetacy.di.builder.DIBuilder
import io.ktor.client.engine.*
import io.ktor.client.engine.cio.*

fun DIBuilder.clientEngine() {
    val clientEngine by factory<HttpClientEngine> {
        CIO.create()
    }
}
