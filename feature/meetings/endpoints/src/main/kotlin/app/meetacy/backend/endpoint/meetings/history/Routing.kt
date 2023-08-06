package app.meetacy.backend.endpoint.meetings.history

import app.meetacy.backend.endpoint.meetings.history.active.meetingsHistoryActive
import app.meetacy.backend.endpoint.meetings.history.list.listMeetingsHistory
import app.meetacy.backend.endpoint.meetings.history.past.meetingsHistoryPast
import io.ktor.server.routing.*

class MeetingsHistoryDependencies

fun Route.meetingsHistory() = route("/history") {
    listMeetingsHistory()
    meetingsHistoryActive()
    meetingsHistoryPast()
}
