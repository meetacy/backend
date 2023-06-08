package app.meetacy.backend.endpoint.meetings.history

import app.meetacy.backend.endpoint.meetings.history.active.ListMeetingsActiveRepository
import app.meetacy.backend.endpoint.meetings.history.active.meetingsHistoryActive
import app.meetacy.backend.endpoint.meetings.history.list.ListMeetingsHistoryRepository
import app.meetacy.backend.endpoint.meetings.history.list.listMeetingsHistory
import io.ktor.server.routing.*

class MeetingsHistoryDependencies(
    val listMeetingsHistoryRepository: ListMeetingsHistoryRepository,
    val meetingsActiveRepository: ListMeetingsActiveRepository
)

fun Route.meetingsHistory(dependencies: MeetingsHistoryDependencies) = route("/history") {
    listMeetingsHistory(dependencies.listMeetingsHistoryRepository)
    meetingsHistoryActive(dependencies.meetingsActiveRepository)
}
