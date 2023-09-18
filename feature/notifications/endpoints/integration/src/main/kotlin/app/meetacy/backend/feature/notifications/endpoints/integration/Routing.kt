package app.meetacy.backend.feature.notifications.endpoints.integration

import app.meetacy.backend.feature.notifications.endpoints.integration.get.list
import app.meetacy.backend.feature.notifications.endpoints.integration.read.read
import app.meetacy.di.DI
import io.ktor.server.routing.*

fun Route.notifications(di: DI) = route("/notifications") {
    list(di)
    read(di)
}
