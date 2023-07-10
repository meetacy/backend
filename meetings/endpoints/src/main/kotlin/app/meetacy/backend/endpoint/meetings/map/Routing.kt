package app.meetacy.backend.endpoint.meetings.map

import app.meetacy.backend.endpoint.meetings.map.list.ListMeetingsMapRepository
import app.meetacy.backend.endpoint.meetings.map.list.listMeetingsMap
import io.ktor.server.routing.*

class MeetingsMapDependencies(
    val listMeetingsMapRepository: ListMeetingsMapRepository
)

fun Route.meetingsMap(
    dependencies: MeetingsMapDependencies
) = route("/map") {
    listMeetingsMap(dependencies.listMeetingsMapRepository)
}
