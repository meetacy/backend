package app.meetacy.backend.feature.meetings.endpoints.integration

import app.meetacy.backend.feature.meetings.endpoints.integration.create.createMeeting
import app.meetacy.backend.feature.meetings.endpoints.integration.delete.deleteMeeting
import app.meetacy.backend.feature.meetings.endpoints.integration.edit.editMeeting
import app.meetacy.backend.feature.meetings.endpoints.integration.get.getMeeting
import app.meetacy.backend.feature.meetings.endpoints.integration.history.meetingsHistory
import app.meetacy.backend.feature.meetings.endpoints.integration.map.meetingsMap
import app.meetacy.backend.feature.meetings.endpoints.integration.participants.meetingParticipants
import app.meetacy.backend.feature.meetings.endpoints.integration.participate.participateMeeting
import app.meetacy.di.DI
import io.ktor.server.routing.*

fun Route.meetings(di: DI) = route("/meetings") {
    meetingsHistory(di)
    meetingsMap(di)
    meetingParticipants(di)

    createMeeting(di)
    deleteMeeting(di)
    editMeeting(di)
    getMeeting(di)
    participateMeeting(di)
}
