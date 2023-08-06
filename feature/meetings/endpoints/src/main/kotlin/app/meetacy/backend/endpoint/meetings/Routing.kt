package app.meetacy.backend.endpoint.meetings

import app.meetacy.backend.endpoint.meetings.create.CreateMeetingRepository
import app.meetacy.backend.endpoint.meetings.create.createMeeting
import app.meetacy.backend.endpoint.meetings.delete.DeleteMeetingRepository
import app.meetacy.backend.endpoint.meetings.delete.deleteMeeting
import app.meetacy.backend.endpoint.meetings.edit.EditMeetingRepository
import app.meetacy.backend.endpoint.meetings.edit.editMeeting
import app.meetacy.backend.endpoint.meetings.get.GetMeetingRepository
import app.meetacy.backend.endpoint.meetings.get.getMeetings
import app.meetacy.backend.endpoint.meetings.history.meetingsHistory
import app.meetacy.backend.endpoint.meetings.map.meetingsMap
import app.meetacy.backend.endpoint.meetings.participants.ParticipantsDependencies
import app.meetacy.backend.endpoint.meetings.participants.meetingParticipants
import app.meetacy.backend.endpoint.meetings.participate.ParticipateMeetingRepository
import app.meetacy.backend.endpoint.meetings.participate.participateMeeting
import io.ktor.server.routing.*

class MeetingsDependencies(
    val meetingParticipantsDependencies: ParticipantsDependencies,
    val getMeetingRepository: GetMeetingRepository,
    val createMeetingRepository: CreateMeetingRepository,
    val participateMeetingRepository: ParticipateMeetingRepository,
    val deleteMeetingRepository: DeleteMeetingRepository,
    val editMeetingRepository: EditMeetingRepository
)

fun Route.meetings(
    dependencies: MeetingsDependencies
) = route("/meetings") {
    meetingsHistory()
    meetingsMap()
    meetingParticipants(dependencies.meetingParticipantsDependencies)

    createMeeting(dependencies.createMeetingRepository)
    deleteMeeting(dependencies.deleteMeetingRepository)
    getMeetings(dependencies.getMeetingRepository)
    participateMeeting(dependencies.participateMeetingRepository)
    editMeeting(dependencies.editMeetingRepository)
}
