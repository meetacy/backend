package app.meetacy.backend.endpoint.meetings

import app.meetacy.backend.endpoint.meetings.create.createMeeting
import app.meetacy.backend.endpoint.meetings.delete.deleteMeeting
import app.meetacy.backend.endpoint.meetings.edit.editMeeting
import app.meetacy.backend.endpoint.meetings.get.getMeetings
import app.meetacy.backend.endpoint.meetings.history.meetingsHistory
import app.meetacy.backend.endpoint.meetings.map.meetingsMap
import app.meetacy.backend.endpoint.meetings.participants.meetingParticipants
import app.meetacy.backend.endpoint.meetings.participate.participateMeeting
import io.ktor.server.routing.*

fun Route.meetings() = route("/meetings") {
    meetingsHistory()
    meetingsMap()
    meetingParticipants()

    createMeeting()
    deleteMeeting()
    getMeetings()
    participateMeeting()
    editMeeting()
}
