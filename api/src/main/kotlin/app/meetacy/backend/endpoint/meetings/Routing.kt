package app.meetacy.backend.endpoint.meetings

import app.meetacy.backend.endpoint.meetings.create.CreateMeetingRepository
import app.meetacy.backend.endpoint.meetings.create.createMeet
import app.meetacy.backend.endpoint.meetings.get.MeetingProvider
import app.meetacy.backend.endpoint.meetings.get.getMeet
import app.meetacy.backend.endpoint.meetings.list.MeetingsProvider
import app.meetacy.backend.endpoint.meetings.list.listMeet
import app.meetacy.backend.endpoint.meetings.participate.ParticipateMeetingRepository
import app.meetacy.backend.endpoint.meetings.participate.participateMeet

import io.ktor.server.routing.*

fun Route.meetings(
    meetingsProvider: MeetingsProvider,
    meetingProvider: MeetingProvider,
    createMeetingRepository: CreateMeetingRepository,
    participateMeetingRepository: ParticipateMeetingRepository
) = route("/meetings") {
    listMeet(meetingsProvider)
    createMeet(createMeetingRepository)
    getMeet(meetingProvider)
    participateMeet(participateMeetingRepository)
}
