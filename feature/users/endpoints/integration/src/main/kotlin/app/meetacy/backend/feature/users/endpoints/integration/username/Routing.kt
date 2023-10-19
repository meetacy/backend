package app.meetacy.backend.feature.users.endpoints.integration.username

import app.meetacy.backend.feature.users.endpoints.integration.username.available.usernameAvailable
import app.meetacy.di.DI
import io.ktor.server.routing.*

fun Route.username(di: DI) = route("/username") {
    usernameAvailable(di)
}
