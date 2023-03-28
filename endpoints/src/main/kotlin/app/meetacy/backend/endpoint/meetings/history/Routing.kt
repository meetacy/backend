package app.meetacy.backend.endpoint.meetings.history

import app.meetacy.backend.endpoint.meetings.history.list.ListMeetingsHistoryRepository
import app.meetacy.backend.endpoint.meetings.history.list.listMeetingsHistory
import io.ktor.server.routing.*

class MeetingsHistoryDependencies(
    val listMeetingsHistoryRepository: ListMeetingsHistoryRepository
)

fun Route.meetingsHistory(dependencies: MeetingsHistoryDependencies) = route("/history") {
    listMeetingsHistory(dependencies.listMeetingsHistoryRepository)
}
