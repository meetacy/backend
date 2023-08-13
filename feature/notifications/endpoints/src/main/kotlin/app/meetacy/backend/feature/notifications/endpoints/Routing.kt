package app.meetacy.backend.feature.notifications.endpoints

import app.meetacy.backend.feature.notifications.endpoints.get.ListNotificationsRepository
import app.meetacy.backend.feature.notifications.endpoints.get.list
import app.meetacy.backend.feature.notifications.endpoints.read.ReadNotificationsRepository
import app.meetacy.backend.feature.notifications.endpoints.read.read
import io.ktor.server.routing.Route
import io.ktor.server.routing.route

class NotificationsDependencies(
    val listNotificationsRepository: ListNotificationsRepository,
    val readNotificationsRepository: ReadNotificationsRepository
)

fun Route.notifications(dependencies: NotificationsDependencies) = route("/notifications") {
    list(dependencies.listNotificationsRepository)
    read(dependencies.readNotificationsRepository)
}
