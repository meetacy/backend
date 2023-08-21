package app.meetacy.backend.feature.meetings.endpoints.integration

import app.meetacy.backend.feature.meetings.endpoints.integration.create.createMeeting
import io.ktor.server.routing.*

fun Route.meetings() = route("/meetings") {
//    meetingsHistory(dependencies.history)
//    meetingsMap(dependencies.map)
//    meetingParticipants(dependencies.participants)

    createMeeting()
//    deleteMeeting(dependencies.delete)
//    getMeetings(dependencies.get)
//    participateMeeting(dependencies.participate)
//    editMeeting(dependencies.edit)
}
