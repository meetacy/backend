package app.meetacy.backend.usecase.meetings.inviteCode.getMeeting

import app.meetacy.backend.types.access.AccessIdentity
import app.meetacy.backend.types.meeting.MeetingId
import app.meetacy.backend.types.meeting.inviteCode.MeetingInviteCode
import app.meetacy.backend.usecase.types.AuthRepository
import app.meetacy.backend.usecase.types.GetMeetingsViewsRepository
import app.meetacy.backend.usecase.types.MeetingView
import app.meetacy.backend.usecase.types.authorizeWithUserId

class GetMeetingByInviteCodeUsecase(
    private val authRepository: AuthRepository,
    private val getMeetingsViewsRepository: GetMeetingsViewsRepository,
    private val storage: Storage
) {

    sealed interface Result {
        class Success(val meeting: MeetingView) : Result
        object InvalidAccessIdentity : Result
        object InvalidInviteCode : Result
    }


    suspend fun getMeeting(
        accessIdentity: AccessIdentity,
        inviteCode: MeetingInviteCode
    ): Result {
        val userId = authRepository.authorizeWithUserId(accessIdentity) { return Result.InvalidAccessIdentity }
        val meetingId = storage.getMeetingIdByInviteCode(inviteCode) ?: return Result.InvalidInviteCode
        val meetingView = getMeetingsViewsRepository.getMeetingsViewsOrNull(userId, listOf(meetingId))
            .firstOrNull() ?: return Result.InvalidInviteCode
        return Result.Success(meetingView)
    }


    interface Storage {
        suspend fun getMeetingIdByInviteCode(inviteCode: MeetingInviteCode): MeetingId?
    }

}