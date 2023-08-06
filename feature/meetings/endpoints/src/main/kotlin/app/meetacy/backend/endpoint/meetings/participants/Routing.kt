package app.meetacy.backend.endpoint.meetings.participants

import app.meetacy.backend.endpoint.meetings.participants.list.listMeetingParticipants
import io.ktor.server.routing.*

fun Route.meetingParticipants() = route("/participants") {
    listMeetingParticipants()
}
