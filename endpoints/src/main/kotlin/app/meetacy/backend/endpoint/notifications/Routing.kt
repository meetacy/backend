package app.meetacy.backend.endpoint.notifications

import app.meetacy.backend.endpoint.notifications.get.GetNotificationsRepository
import app.meetacy.backend.endpoint.notifications.get.get
import app.meetacy.backend.endpoint.notifications.read.ReadNotificationsRepository
import app.meetacy.backend.endpoint.notifications.read.read
import io.ktor.server.routing.Route
import io.ktor.server.routing.route

class NotificationsDependencies(
    val getNotificationsRepository: GetNotificationsRepository,
    val readNotificationsRepository: ReadNotificationsRepository
)

fun Route.notifications(dependencies: NotificationsDependencies) = route("/notifications") {
    get(dependencies.getNotificationsRepository)
    read(dependencies.readNotificationsRepository)
}
