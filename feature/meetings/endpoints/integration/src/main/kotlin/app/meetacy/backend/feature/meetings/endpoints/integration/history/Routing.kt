package app.meetacy.backend.feature.meetings.endpoints.integration.history

import app.meetacy.backend.feature.meetings.endpoints.integration.history.active.listMeetingsActive
import app.meetacy.backend.feature.meetings.endpoints.integration.history.list.listMeetingsHistory
import app.meetacy.backend.feature.meetings.endpoints.integration.history.past.listMeetingsPast
import app.meetacy.di.DI
import io.ktor.server.routing.*

internal fun Route.meetingsHistory(di: DI) = route("/history") {
    listMeetingsHistory(di)
    listMeetingsActive(di)
    listMeetingsPast(di)
}
