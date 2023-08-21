package app.meetacy.backend.feature.meetings.endpoints

import app.meetacy.backend.feature.meetings.endpoints.create.CreateMeetingRepository
import app.meetacy.backend.feature.meetings.endpoints.create.createMeeting
import app.meetacy.backend.feature.meetings.endpoints.delete.DeleteMeetingRepository
import app.meetacy.backend.feature.meetings.endpoints.delete.deleteMeeting
import app.meetacy.backend.feature.meetings.endpoints.edit.EditMeetingRepository
import app.meetacy.backend.feature.meetings.endpoints.edit.editMeeting
import app.meetacy.backend.feature.meetings.endpoints.get.GetMeetingRepository
import app.meetacy.backend.feature.meetings.endpoints.get.getMeetings
import app.meetacy.backend.feature.meetings.endpoints.history.MeetingsHistoryDependencies
import app.meetacy.backend.feature.meetings.endpoints.history.meetingsHistory
import app.meetacy.backend.feature.meetings.endpoints.map.MeetingsMapDependencies
import app.meetacy.backend.feature.meetings.endpoints.map.meetingsMap
import app.meetacy.backend.feature.meetings.endpoints.participants.MeetingParticipantsDependencies
import app.meetacy.backend.feature.meetings.endpoints.participants.meetingParticipants
import app.meetacy.backend.feature.meetings.endpoints.participate.ParticipateMeetingRepository
import app.meetacy.backend.feature.meetings.endpoints.participate.participateMeeting
import io.ktor.server.routing.*

class MeetingDependencies(
    val history: MeetingsHistoryDependencies,
    val map: MeetingsMapDependencies,
    val participants: MeetingParticipantsDependencies,
    val create: CreateMeetingRepository,
    val delete: DeleteMeetingRepository,
    val get: GetMeetingRepository,
    val participate: ParticipateMeetingRepository,
    val edit: EditMeetingRepository
)

fun Route.meetings(dependencies: MeetingDependencies) = route("/meetings") {
    meetingsHistory(dependencies.history)
    meetingsMap(dependencies.map)
    meetingParticipants(dependencies.participants)

    createMeeting(dependencies.create)
    deleteMeeting(dependencies.delete)
    getMeetings(dependencies.get)
    participateMeeting(dependencies.participate)
    editMeeting(dependencies.edit)
}
