package app.meetacy.backend.feature.meetings.endpoints.history

import app.meetacy.backend.feature.meetings.endpoints.history.active.meetingsHistoryActive
import app.meetacy.backend.feature.meetings.endpoints.history.list.listMeetingsHistory
import app.meetacy.backend.feature.meetings.endpoints.history.past.meetingsHistoryPast
import io.ktor.server.routing.*

class MeetingsHistoryDependencies

fun Route.meetingsHistory() = route("/history") {
    listMeetingsHistory()
    meetingsHistoryActive()
    meetingsHistoryPast()
}
