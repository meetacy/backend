package app.meetacy.backend.application.endpoints.deeplink

import app.meetacy.backend.application.endpoints.deeplink.user.userDeeplinks
import io.ktor.server.routing.*

fun Route.deeplinks() {
    userDeeplinks()
}
