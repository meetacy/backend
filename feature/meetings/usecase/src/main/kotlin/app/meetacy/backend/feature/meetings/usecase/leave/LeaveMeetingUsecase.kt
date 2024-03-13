package app.meetacy.backend.feature.meetings.usecase.leave

import app.meetacy.backend.types.access.AccessIdentity
import app.meetacy.backend.types.auth.AuthRepository
import app.meetacy.backend.types.auth.authorizeWithUserId
import app.meetacy.backend.types.meetings.GetMeetingsViewsRepository
import app.meetacy.backend.types.meetings.MeetingId
import app.meetacy.backend.types.meetings.MeetingIdentity
import app.meetacy.backend.types.meetings.getMeetingViewOrNull
import app.meetacy.backend.types.users.UserId

class LeaveMeetingUsecase(
    private val authRepository: AuthRepository,
    private val getMeetingsViewsRepository: GetMeetingsViewsRepository,
    private val storage: Storage
) {
    suspend fun leaveMeeting(
        accessIdentity: AccessIdentity,
        meetingIdentity: MeetingIdentity
    ): Result {
        val userId = authRepository.authorizeWithUserId(accessIdentity) { return Result.InvalidIdentity }

        val meeting = getMeetingsViewsRepository
            .getMeetingViewOrNull(userId, meetingIdentity.id)
            ?: return Result.MeetingNotFound

        if (meetingIdentity.accessHash != meeting.identity.accessHash)
            return Result.MeetingNotFound

        storage.leaveMeeting(meetingIdentity.id, userId)
        return Result.Success
    }

    sealed interface Result {
        data object Success : Result
        data object InvalidIdentity : Result
        data object MeetingNotFound : Result
    }

    interface Storage {
        suspend fun leaveMeeting(meetingId: MeetingId, userId: UserId)
    }
}
