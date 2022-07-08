package app.meetacy.backend.endpoint

import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.install
import io.ktor.server.cio.CIO
import io.ktor.server.engine.embeddedServer
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.routing.routing
import app.meetacy.backend.endpoint.auth.auth
import app.meetacy.backend.endpoint.auth.email.confirm.ConfirmStorage
import app.meetacy.backend.endpoint.auth.email.link.LinkEmailStorage
import app.meetacy.backend.endpoint.auth.email.link.Mailer

fun startEndpoints(
    port: Int,
    wait: Boolean,
    mailer: Mailer,
    linkEmailStorage: LinkEmailStorage,
    confirmStorage: ConfirmStorage
) = embeddedServer(CIO, port) {
    install(ContentNegotiation) {
        json()
    }

    routing {
        auth(mailer, linkEmailStorage, confirmStorage)
    }
}.start(wait)
