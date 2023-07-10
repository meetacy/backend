package app.meetacy.backend.endpoint.meetings.participants

import app.meetacy.backend.endpoint.meetings.participants.list.ListMeetingParticipantsRepository
import app.meetacy.backend.endpoint.meetings.participants.list.listMeetingParticipants
import io.ktor.server.routing.*

class ParticipantsDependencies(
    val listMeetingParticipantsRepository: ListMeetingParticipantsRepository
)

fun Route.meetingParticipants(dependencies: ParticipantsDependencies) = route("/participants") {
    listMeetingParticipants(dependencies.listMeetingParticipantsRepository)
}
