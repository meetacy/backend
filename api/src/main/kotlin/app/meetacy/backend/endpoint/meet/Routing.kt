package app.meetacy.backend.endpoint.meet

import app.meetacy.backend.endpoint.meet.create.createMeet
import app.meetacy.backend.endpoint.meet.get.getMeet
import app.meetacy.backend.endpoint.meet.list.listMeet

import io.ktor.server.routing.*

fun Route.meetings() = route("/meetings") {
    listMeet()
    createMeet()
    getMeet()
}
