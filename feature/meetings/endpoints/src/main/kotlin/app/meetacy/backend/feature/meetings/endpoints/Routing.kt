package app.meetacy.backend.feature.meetings.endpoints

import app.meetacy.backend.feature.meetings.endpoints.create.createMeeting
import app.meetacy.backend.feature.meetings.endpoints.delete.deleteMeeting
import app.meetacy.backend.feature.meetings.endpoints.edit.editMeeting
import app.meetacy.backend.feature.meetings.endpoints.get.getMeetings
import app.meetacy.backend.feature.meetings.endpoints.history.meetingsHistory
import app.meetacy.backend.feature.meetings.endpoints.map.meetingsMap
import app.meetacy.backend.feature.meetings.endpoints.participants.meetingParticipants
import app.meetacy.backend.feature.meetings.endpoints.participate.participateMeeting
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
