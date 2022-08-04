package app.meetacy.backend.endpoint.meetings

import app.meetacy.backend.endpoint.meetings.create.CreateMeetingRepository
import app.meetacy.backend.endpoint.meetings.create.createMeeting
import app.meetacy.backend.endpoint.meetings.get.GetMeetingRepository
import app.meetacy.backend.endpoint.meetings.get.getMeeting
import app.meetacy.backend.endpoint.meetings.list.MeetingsListRepository
import app.meetacy.backend.endpoint.meetings.list.listMeetings
import app.meetacy.backend.endpoint.meetings.participate.ParticipateMeetingRepository
import app.meetacy.backend.endpoint.meetings.participate.participateMeeting

import io.ktor.server.routing.*

class MeetingsDependencies(
    val meetingsListRepository: MeetingsListRepository,
    val getMeetingRepository: GetMeetingRepository,
    val createMeetingRepository: CreateMeetingRepository,
    val participateMeetingRepository: ParticipateMeetingRepository
)

fun Route.meetings(
    dependencies: MeetingsDependencies
) = route("/meetings") {
    listMeetings(dependencies.meetingsListRepository)
    createMeeting(dependencies.createMeetingRepository)
    getMeeting(dependencies.getMeetingRepository)
    participateMeeting(dependencies.participateMeetingRepository)
}
