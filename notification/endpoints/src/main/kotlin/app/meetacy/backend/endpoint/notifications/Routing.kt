package app.meetacy.backend.endpoint.notifications

import app.meetacy.backend.endpoint.notifications.get.ListNotificationsRepository
import app.meetacy.backend.endpoint.notifications.get.list
import app.meetacy.backend.endpoint.notifications.read.ReadNotificationsRepository
import app.meetacy.backend.endpoint.notifications.read.read
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
