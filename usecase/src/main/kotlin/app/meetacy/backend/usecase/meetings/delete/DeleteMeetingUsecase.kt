package app.meetacy.backend.usecase.meetings.delete

import app.meetacy.backend.types.AccessIdentity
import app.meetacy.backend.types.MeetingId
import app.meetacy.backend.types.MeetingIdentity
import app.meetacy.backend.usecase.types.AuthRepository
import app.meetacy.backend.usecase.types.GetMeetingsViewsRepository
import app.meetacy.backend.usecase.types.authorizeWithUserId

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
            .getMeetingsViewsOrNull(userId, listOf(meetingIdentity.meetingId))
            .first()
            ?: return Result.MeetingNotFound

        if (meetingIdentity.accessHash != meeting.identity.accessHash)
            return Result.MeetingNotFound

        if (meeting.creator.identity.userId != userId) return Result.InvalidIdentity

        storage.deleteMeeting(meetingIdentity.meetingId)
        return Result.Success
    }

    sealed interface Result {
        object Success : Result
        object InvalidIdentity : Result
        object MeetingNotFound : Result
    }

    interface Storage {
        suspend fun deleteMeeting(meetingId: MeetingId)
    }
}
