package app.meetacy.backend.feature.meetings.endpoints.integration.history

import app.meetacy.backend.feature.meetings.endpoints.integration.history.active.listMeetingsActive
import app.meetacy.backend.feature.meetings.endpoints.integration.history.list.listMeetingsHistory
import app.meetacy.backend.feature.meetings.endpoints.integration.history.past.listMeetingsPast
import io.ktor.server.routing.*

internal fun Route.meetingsHistory() = route("/history") {
    listMeetingsHistory()
    listMeetingsActive()
    listMeetingsPast()
}
