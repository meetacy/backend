package app.meetacy.backend.endpoint.meetings

import app.meetacy.backend.endpoint.meetings.create.CreateMeetingRepository
import app.meetacy.backend.endpoint.meetings.create.createMeeting
import app.meetacy.backend.endpoint.meetings.get.MeetingProvider
import app.meetacy.backend.endpoint.meetings.get.getMeeting
import app.meetacy.backend.endpoint.meetings.list.MeetingsProvider
import app.meetacy.backend.endpoint.meetings.list.listMeetings
import app.meetacy.backend.endpoint.meetings.participate.ParticipateMeetingRepository
import app.meetacy.backend.endpoint.meetings.participate.participateMeeting

import io.ktor.server.routing.*

class MeetingsDependencies(
    val meetingsProvider: MeetingsProvider,
    val meetingProvider: MeetingProvider,
    val createMeetingRepository: CreateMeetingRepository,
    val participateMeetingRepository: ParticipateMeetingRepository
)

fun Route.meetings(
    dependencies: MeetingsDependencies
) = route("/meetings") {
    listMeetings(dependencies.meetingsProvider)
    createMeeting(dependencies.createMeetingRepository)
    getMeeting(dependencies.meetingProvider)
    participateMeeting(dependencies.participateMeetingRepository)
}
