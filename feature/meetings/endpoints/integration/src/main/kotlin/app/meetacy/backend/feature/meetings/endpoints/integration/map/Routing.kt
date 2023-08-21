package app.meetacy.backend.feature.meetings.endpoints.map

import app.meetacy.backend.feature.meetings.endpoints.map.list.ListMeetingsMapRepository
import app.meetacy.backend.feature.meetings.endpoints.map.list.listMeetingsMap
import io.ktor.server.routing.*

class MeetingsMapDependencies(
    val listMeetingsProvider: ListMeetingsMapRepository
)

fun Route.meetingsMap(dependencies: MeetingsMapDependencies) = route("/map") {
    listMeetingsMap(dependencies.listMeetingsProvider)
}
