package app.meetacy.backend.feature.meetings.endpoints.integration.map

import app.meetacy.backend.feature.meetings.endpoints.integration.map.list.listMeetingsMap
import io.ktor.server.routing.*

internal fun Route.meetingsMap() = route("/map") {
    listMeetingsMap()
}
