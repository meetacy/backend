package app.meetacy.backend.endpoint.meetings.inviteCode

import app.meetacy.backend.endpoint.meetings.inviteCode.create.CreateInviteCodeForMeetingRepository
import app.meetacy.backend.endpoint.meetings.inviteCode.create.createInviteCode
import app.meetacy.backend.endpoint.meetings.inviteCode.getMeeting.GetMeetingByInviteCodeRepository
import app.meetacy.backend.endpoint.meetings.inviteCode.getMeeting.getMeetingByInviteCode
import io.ktor.server.routing.*

class MeetingsInviteCodeDependencies(
    val createInviteCodeForMeetingRepository: CreateInviteCodeForMeetingRepository,
    val getMeetingByInviteCodeRepository: GetMeetingByInviteCodeRepository
)

fun Route.inviteCode(
    dependencies: MeetingsInviteCodeDependencies
) = route("/inviteCode") {
    createInviteCode(dependencies.createInviteCodeForMeetingRepository)
    getMeetingByInviteCode(dependencies.getMeetingByInviteCodeRepository)
}
