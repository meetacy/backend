package app.meetacy.backend.feature.email.endpoints.integration

import app.meetacy.backend.feature.email.endpoints.integration.confirm.confirmEmail
import app.meetacy.backend.feature.email.endpoints.integration.link.linkEmail
import app.meetacy.di.DI
import io.ktor.server.routing.*

fun Route.email(di: DI) = route("/email") {
    linkEmail(di)
    confirmEmail(di)
}
