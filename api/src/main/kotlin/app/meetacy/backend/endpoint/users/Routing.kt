package app.meetacy.backend.endpoint.users

import io.ktor.server.application.call
import io.ktor.server.response.respond
import io.ktor.server.routing.Routing
import io.ktor.server.routing.get

fun Routing.demoUsers(usersProvider: UsersProvider) =
    get("/demo-users") {
        call.respond(usersProvider.getUsers())
    }
