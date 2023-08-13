package app.meetacy.backend.feature.meetings.endpoints.participants

import app.meetacy.backend.feature.meetings.endpoints.participants.list.listMeetingParticipants
import io.ktor.server.routing.*

fun Route.meetingParticipants() = route("/participants") {
    listMeetingParticipants()
}
