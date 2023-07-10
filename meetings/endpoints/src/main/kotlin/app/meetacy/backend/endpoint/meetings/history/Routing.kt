package app.meetacy.backend.endpoint.meetings.history

import app.meetacy.backend.endpoint.meetings.history.active.ListMeetingsActiveRepository
import app.meetacy.backend.endpoint.meetings.history.active.meetingsHistoryActive
import app.meetacy.backend.endpoint.meetings.history.list.ListMeetingsHistoryRepository
import app.meetacy.backend.endpoint.meetings.history.list.listMeetingsHistory
import app.meetacy.backend.endpoint.meetings.history.past.ListMeetingsPastRepository
import app.meetacy.backend.endpoint.meetings.history.past.meetingsHistoryPast
import io.ktor.server.routing.*

class MeetingsHistoryDependencies(
    val listMeetingsHistoryRepository: ListMeetingsHistoryRepository,
    val meetingsActiveRepository: ListMeetingsActiveRepository,
    val meetingsPastRepository: ListMeetingsPastRepository
)

fun Route.meetingsHistory(dependencies: MeetingsHistoryDependencies) = route("/history") {
    listMeetingsHistory(dependencies.listMeetingsHistoryRepository)
    meetingsHistoryActive(dependencies.meetingsActiveRepository)
    meetingsHistoryPast(dependencies.meetingsPastRepository)
}
