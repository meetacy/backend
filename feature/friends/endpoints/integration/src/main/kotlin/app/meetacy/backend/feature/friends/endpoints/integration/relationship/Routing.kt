package app.meetacy.backend.feature.friends.endpoints.integration.relationship

import app.meetacy.backend.feature.friends.endpoints.integration.relationship.subscribers.subscribers
import app.meetacy.backend.feature.friends.endpoints.integration.relationship.subscriptions.subscriptions
import app.meetacy.di.DI
import io.ktor.server.routing.*

fun Route.relationship(di: DI) = route("/relationship") {
    subscribers(di)
    subscriptions(di)
}
