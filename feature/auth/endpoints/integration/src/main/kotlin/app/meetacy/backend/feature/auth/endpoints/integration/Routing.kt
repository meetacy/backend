package app.meetacy.backend.feature.auth.endpoints.integration

import app.meetacy.backend.feature.auth.endpoints.integration.generate.generateToken
import app.meetacy.backend.feature.email.endpoints.integration.email
import app.meetacy.backend.feature.telegram.endpoints.integration.telegram
import app.meetacy.di.DI
import io.ktor.server.routing.*

fun Route.auth(di: DI) = route("/auth") {
    email(di)
    telegram(di)
    generateToken(di)
}
