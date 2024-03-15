package app.meetacy.backend.feature.friends.endpoints.integration.subscribers

import app.meetacy.backend.feature.friends.endpoints.integration.subscribers.list.listSubscribers
import app.meetacy.di.DI
import io.ktor.server.routing.*

fun Route.subscribers(di: DI) = route("subscribers") {
    listSubscribers(di)
}
