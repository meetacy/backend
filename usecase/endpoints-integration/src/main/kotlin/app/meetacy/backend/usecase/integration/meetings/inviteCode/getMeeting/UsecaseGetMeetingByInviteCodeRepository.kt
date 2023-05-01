package app.meetacy.backend.usecase.integration.meetings.inviteCode.getMeeting

import app.meetacy.backend.endpoint.meetings.inviteCode.getMeeting.GetMeetingByInviteCodeParams
import app.meetacy.backend.endpoint.meetings.inviteCode.getMeeting.GetMeetingByInviteCodeRepository
import app.meetacy.backend.endpoint.meetings.inviteCode.getMeeting.GetMeetingByInviteCodeResult
import app.meetacy.backend.usecase.integration.types.mapToEndpoint
import app.meetacy.backend.usecase.meetings.inviteCode.getMeeting.GetMeetingByInviteCodeUsecase

class UsecaseGetMeetingByInviteCodeRepository(
    private val usecase: GetMeetingByInviteCodeUsecase
) : GetMeetingByInviteCodeRepository {

    override suspend fun create(params: GetMeetingByInviteCodeParams): GetMeetingByInviteCodeResult = when(
        val result = usecase.getMeeting(params.token.type(), params.inviteCode.type())
    ) {
        is GetMeetingByInviteCodeUsecase.Result.Success ->
            GetMeetingByInviteCodeResult.Success(result.meeting.mapToEndpoint())
        is GetMeetingByInviteCodeUsecase.Result.InvalidAccessIdentity ->
            GetMeetingByInviteCodeResult.InvalidAccessIdentity
        is GetMeetingByInviteCodeUsecase.Result.InvalidInviteCode ->
            GetMeetingByInviteCodeResult.InvalidInviteCode
    }

}