package app.meetacy.backend.feature.meetings.endpoints.integration.participants

import app.meetacy.backend.feature.meetings.endpoints.integration.participants.list.listMeetingParticipants
import io.ktor.server.routing.*

internal fun Route.meetingParticipants() = route("/participants") {
    listMeetingParticipants()
}
