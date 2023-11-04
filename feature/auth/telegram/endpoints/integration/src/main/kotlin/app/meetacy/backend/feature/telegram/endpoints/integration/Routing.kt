package app.meetacy.backend.feature.telegram.endpoints.integration

import app.meetacy.backend.feature.telegram.endpoints.integration.prelogin.telegramPrelogin
import app.meetacy.di.DI
import io.ktor.server.routing.Route
import io.ktor.server.routing.route

fun Route.telegram(di: DI) = route("/telegram") {
    telegramPrelogin(di)
}
