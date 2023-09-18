package app.meetacy.backend.feature.meetings.usecase.delete

import app.meetacy.backend.types.access.AccessIdentity
import app.meetacy.backend.types.auth.AuthRepository
import app.meetacy.backend.types.auth.authorizeWithUserId
import app.meetacy.backend.types.meetings.GetMeetingsViewsRepository
import app.meetacy.backend.types.meetings.MeetingId
import app.meetacy.backend.types.meetings.MeetingIdentity
import app.meetacy.backend.types.meetings.getMeetingViewOrNull

class DeleteMeetingUsecase(
    private val authRepository: AuthRepository,
    private val getMeetingsViewsRepository: GetMeetingsViewsRepository,
    private val storage: Storage
) {
    suspend fun deleteMeeting(
        accessIdentity: AccessIdentity,
        meetingIdentity: MeetingIdentity
    ): Result {
        val userId = authRepository.authorizeWithUserId(accessIdentity) { return Result.InvalidIdentity }

        val meeting = getMeetingsViewsRepository
            .getMeetingViewOrNull(userId, meetingIdentity.id)
            ?: return Result.MeetingNotFound

        if (meetingIdentity.accessHash != meeting.identity.accessHash)
            return Result.MeetingNotFound

        if (meeting.creator.identity.id != userId) return Result.InvalidIdentity

        storage.deleteMeeting(meetingIdentity.id)
        return Result.Success
    }

    sealed interface Result {
        data object Success : Result
        data object InvalidIdentity : Result
        data object MeetingNotFound : Result
    }

    interface Storage {
        suspend fun deleteMeeting(meetingId: MeetingId)
    }
}
