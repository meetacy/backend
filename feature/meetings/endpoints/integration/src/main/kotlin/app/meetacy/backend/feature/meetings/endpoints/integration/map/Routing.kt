package app.meetacy.backend.feature.meetings.endpoints.integration.map

import app.meetacy.backend.feature.meetings.endpoints.integration.map.list.listMeetingsMap
import io.ktor.server.routing.*

fun Route.meetingsMap() = route("/map") {
    listMeetingsMap()
}
