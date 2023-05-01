package app.meetacy.backend.usecase.meetings.inviteCode.list

import app.meetacy.backend.types.access.AccessIdentity
import app.meetacy.backend.types.meeting.MeetingId
import app.meetacy.backend.types.meeting.MeetingIdentity
import app.meetacy.backend.types.meeting.inviteCode.MeetingInviteCode
import app.meetacy.backend.usecase.types.AuthRepository
import app.meetacy.backend.usecase.types.GetMeetingsViewsRepository
import app.meetacy.backend.usecase.types.authorizeWithUserId
import app.meetacy.backend.usecase.types.getMeetingsViews

class GetMeetingInviteCodesUsecase(
    private val authRepository: AuthRepository,
    private val getMeetingsViewsRepository: GetMeetingsViewsRepository,
    private val storage: Storage
) {

    sealed interface Result {
        class Success(val inviteCodes: List<MeetingInviteCode>) : Result
        object InvalidAccessIdentity : Result
        object InvalidMeetingIdentity : Result
        object CannotAccess : Result
    }

    suspend fun getMeetingInviteCodes(
        accessIdentity: AccessIdentity,
        meetingIdentity: MeetingIdentity
    ): Result {
        val viewerId = authRepository.authorizeWithUserId(accessIdentity) { return Result.InvalidAccessIdentity }
        val meetingView = getMeetingsViewsRepository.getMeetingsViews(viewerId, listOf(meetingIdentity.id))
            .firstOrNull() ?: return Result.InvalidMeetingIdentity

        if (viewerId != meetingView.creator.identity.userId)
            return Result.CannotAccess

        if (meetingIdentity != meetingView.identity)
            return Result.InvalidMeetingIdentity

        val meetingInviteCodes = storage.getMeetingInviteCodes(meetingView.id)
        return Result.Success(meetingInviteCodes)
    }


    interface Storage {
        suspend fun getMeetingInviteCodes(meetingId: MeetingId): List<MeetingInviteCode>
    }

}