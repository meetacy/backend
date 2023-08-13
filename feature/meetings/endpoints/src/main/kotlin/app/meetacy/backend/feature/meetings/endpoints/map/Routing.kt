package app.meetacy.backend.feature.meetings.endpoints.map

import app.meetacy.backend.feature.meetings.endpoints.map.list.listMeetingsMap
import io.ktor.server.routing.*

fun Route.meetingsMap() = route("/map") {
    listMeetingsMap()
}
