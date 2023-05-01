package app.meetacy.backend.endpoint.meetings.inviteCode

import app.meetacy.backend.endpoint.meetings.inviteCode.create.CreateInviteCodeForMeetingRepository
import app.meetacy.backend.endpoint.meetings.inviteCode.create.createInviteCode
import app.meetacy.backend.endpoint.meetings.inviteCode.getMeeting.GetMeetingByInviteCodeRepository
import app.meetacy.backend.endpoint.meetings.inviteCode.getMeeting.getMeetingByInviteCode
import app.meetacy.backend.endpoint.meetings.inviteCode.list.GetMeetingInviteCodesRepository
import app.meetacy.backend.endpoint.meetings.inviteCode.list.getMeetingInviteCodes
import io.ktor.server.routing.*

class MeetingsInviteCodeDependencies(
    val createInviteCodeForMeetingRepository: CreateInviteCodeForMeetingRepository,
    val getMeetingByInviteCodeRepository: GetMeetingByInviteCodeRepository,
    val getMeetingInviteCodesRepository: GetMeetingInviteCodesRepository
)

fun Route.inviteCode(
    dependencies: MeetingsInviteCodeDependencies
) = route("/inviteCode") {
    createInviteCode(dependencies.createInviteCodeForMeetingRepository)
    getMeetingByInviteCode(dependencies.getMeetingByInviteCodeRepository)
    getMeetingInviteCodes(dependencies.getMeetingInviteCodesRepository)
}
