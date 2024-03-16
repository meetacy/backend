package app.meetacy.backend.feature.friends.endpoints.integration.subscriptions

import app.meetacy.backend.feature.friends.endpoints.integration.subscriptions.list.listSubscriptions
import app.meetacy.di.DI
import io.ktor.server.routing.*

fun Route.subscriptions(di: DI) = route("subscriptions") {
    listSubscriptions(di)
}
