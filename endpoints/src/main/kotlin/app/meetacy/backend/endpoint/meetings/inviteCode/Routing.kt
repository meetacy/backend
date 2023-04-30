package app.meetacy.backend.endpoint.meetings.inviteCode

import app.meetacy.backend.endpoint.meetings.inviteCode.create.CreateInviteCodeForMeetingRepository
import app.meetacy.backend.endpoint.meetings.inviteCode.create.createInviteCode
import io.ktor.server.routing.*

class MeetingsInviteCodeDependencies(
    val createInviteCodeForMeetingRepository: CreateInviteCodeForMeetingRepository
)

fun Route.inviteCode(
    dependencies: MeetingsInviteCodeDependencies
) = route("/inviteCode") {
    createInviteCode(dependencies.createInviteCodeForMeetingRepository)
}
