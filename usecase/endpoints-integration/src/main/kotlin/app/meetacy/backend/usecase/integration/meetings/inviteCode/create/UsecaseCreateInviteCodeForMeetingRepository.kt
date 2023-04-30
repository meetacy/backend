package app.meetacy.backend.usecase.integration.meetings.inviteCode.create

import app.meetacy.backend.endpoint.meetings.inviteCode.create.CreateInviteCodeForMeetingParams
import app.meetacy.backend.endpoint.meetings.inviteCode.create.CreateInviteCodeForMeetingRepository
import app.meetacy.backend.endpoint.meetings.inviteCode.create.CreateInviteCodeForMeetingResult
import app.meetacy.backend.types.access.AccessIdentity
import app.meetacy.backend.types.meeting.MeetingIdentity
import app.meetacy.backend.usecase.meetings.inviteCode.create.CreateInviteCodeForMeetingUsecase

class UsecaseCreateInviteCodeForMeetingRepository(
    private val usecase: CreateInviteCodeForMeetingUsecase
) : CreateInviteCodeForMeetingRepository {

    override suspend fun create(params: CreateInviteCodeForMeetingParams): CreateInviteCodeForMeetingResult =
        when (val it = usecase.create(params.token.type(), params.meetingIdentity.type())) {
            is CreateInviteCodeForMeetingUsecase.Result.Success ->
                CreateInviteCodeForMeetingResult.Success(it.meetingInviteCode)

            is CreateInviteCodeForMeetingUsecase.Result.InvalidAccessIdentity ->
                CreateInviteCodeForMeetingResult.InvalidAccessIdentity
        }

}