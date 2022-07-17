package app.meetacy.backend.endpoint.meet

import app.meetacy.backend.endpoint.meet.create.CreateMeetResult
import app.meetacy.backend.endpoint.meet.create.createMeet
import app.meetacy.backend.endpoint.meet.get.GetMeeting
import app.meetacy.backend.endpoint.meet.get.getMeet
import app.meetacy.backend.endpoint.meet.list.GetListMeet
import app.meetacy.backend.endpoint.meet.list.listMeet
import app.meetacy.backend.endpoint.meet.participate.ParticipateMeeting
import app.meetacy.backend.endpoint.meet.participate.participateMeet

import io.ktor.server.routing.*

fun Route.meetings(
    getListMeet: GetListMeet,
    getMeeting: GetMeeting,
    createMeetResult: CreateMeetResult,
    participateMeeting: ParticipateMeeting
) = route("/meetings") {
    listMeet(getListMeet)
    createMeet(createMeetResult)
    getMeet(getMeeting)
    participateMeet(participateMeeting)
}
