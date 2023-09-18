package app.meetacy.backend.feature.meetings.endpoints.integration.participants

import app.meetacy.backend.feature.meetings.endpoints.integration.participants.list.listMeetingParticipants
import app.meetacy.di.DI
import io.ktor.server.routing.*

internal fun Route.meetingParticipants(di: DI) = route("/participants") {
    listMeetingParticipants(di)
}
