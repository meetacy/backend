package app.meetacy.backend.feature.notifications.endpoints.integration

import app.meetacy.backend.feature.notifications.endpoints.integration.get.list
import app.meetacy.backend.feature.notifications.endpoints.integration.read.read
import io.ktor.server.routing.*

fun Route.notifications() = route("/notifications") {
    list()
    read()
}
