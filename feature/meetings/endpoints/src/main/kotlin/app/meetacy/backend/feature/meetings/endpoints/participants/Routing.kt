package app.meetacy.backend.feature.meetings.endpoints.participants

import app.meetacy.backend.feature.meetings.endpoints.participants.list.ListMeetingParticipantsRepository
import app.meetacy.backend.feature.meetings.endpoints.participants.list.listMeetingParticipants
import io.ktor.server.routing.*

class MeetingParticipantsDependencies(
    val listMeetingParticipantsProvider: ListMeetingParticipantsRepository
)

fun Route.meetingParticipants(dependencies: MeetingParticipantsDependencies) = route("/participants") {
    listMeetingParticipants(dependencies.listMeetingParticipantsProvider)
}
