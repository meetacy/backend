package app.meetacy.backend.endpoint.meetings.map

import app.meetacy.backend.endpoint.meetings.map.list.listMeetingsMap
import io.ktor.server.routing.*

fun Route.meetingsMap() = route("/map") {
    listMeetingsMap()
}
