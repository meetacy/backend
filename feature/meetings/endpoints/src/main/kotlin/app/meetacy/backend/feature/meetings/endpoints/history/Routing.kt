package app.meetacy.backend.feature.meetings.endpoints.history

import app.meetacy.backend.feature.meetings.endpoints.history.active.ListMeetingsActiveRepository
import app.meetacy.backend.feature.meetings.endpoints.history.active.meetingsHistoryActive
import app.meetacy.backend.feature.meetings.endpoints.history.list.ListMeetingsHistoryRepository
import app.meetacy.backend.feature.meetings.endpoints.history.list.listMeetingsHistory
import app.meetacy.backend.feature.meetings.endpoints.history.past.ListMeetingsPastRepository
import app.meetacy.backend.feature.meetings.endpoints.history.past.meetingsHistoryPast
import io.ktor.server.routing.*

class MeetingsHistoryDependencies(
    val historyProvider: ListMeetingsHistoryRepository,
    val activeProvider: ListMeetingsActiveRepository,
    val pastProvider: ListMeetingsPastRepository
)

fun Route.meetingsHistory(dependencies: MeetingsHistoryDependencies) = route("/history") {
    listMeetingsHistory(dependencies.historyProvider)
    meetingsHistoryActive(dependencies.activeProvider)
    meetingsHistoryPast(dependencies.pastProvider)
}
