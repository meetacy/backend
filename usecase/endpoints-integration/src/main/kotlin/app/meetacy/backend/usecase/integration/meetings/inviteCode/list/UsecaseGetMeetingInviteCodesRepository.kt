package app.meetacy.backend.usecase.integration.meetings.inviteCode.list

import app.meetacy.backend.endpoint.meetings.inviteCode.getMeeting.GetMeetingByInviteCodeResult
import app.meetacy.backend.endpoint.meetings.inviteCode.list.GetMeetingInviteCodesParams
import app.meetacy.backend.endpoint.meetings.inviteCode.list.GetMeetingInviteCodesRepository
import app.meetacy.backend.endpoint.meetings.inviteCode.list.GetMeetingInviteCodesResult
import app.meetacy.backend.usecase.meetings.inviteCode.list.GetMeetingInviteCodesUsecase

class UsecaseGetMeetingInviteCodesRepository(
    private val usecase: GetMeetingInviteCodesUsecase
) : GetMeetingInviteCodesRepository {

    override suspend fun getMeetingInviteCodes(params: GetMeetingInviteCodesParams): GetMeetingInviteCodesResult = when(
        val result = usecase.getMeetingInviteCodes(
            accessIdentity = params.token.type(),
            meetingIdentity = params.meetingIdentity.type(),
            amount = params.amount.type(),
            pagingId = params.pagingId?.type()
        )
    ) {
        is GetMeetingInviteCodesUsecase.Result.Success ->
            GetMeetingInviteCodesResult.Success(result.inviteCodes)
        is GetMeetingInviteCodesUsecase.Result.InvalidAccessIdentity ->
            GetMeetingInviteCodesResult.InvalidAccessIdentity
        is GetMeetingInviteCodesUsecase.Result.InvalidMeetingIdentity ->
            GetMeetingInviteCodesResult.InvalidMeetingIdentity
        is GetMeetingInviteCodesUsecase.Result.CannotAccess ->
            GetMeetingInviteCodesResult.CannotAccess
    }

}