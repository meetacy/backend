package app.meetacy.backend.usecase.meetings.delete

import app.meetacy.backend.types.access.AccessIdentity
import app.meetacy.backend.types.auth.AuthRepository
import app.meetacy.backend.types.auth.authorizeWithUserId
import app.meetacy.backend.types.meeting.MeetingId
import app.meetacy.backend.types.meeting.MeetingIdentity
import app.meetacy.backend.usecase.types.GetMeetingsViewsRepository

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
            .getMeetingsViewsOrNull(userId, listOf(meetingIdentity.id))
            .first()
            ?: return Result.MeetingNotFound

        if (meetingIdentity.accessHash != meeting.identity.accessHash)
            return Result.MeetingNotFound

        if (meeting.creator.identity.id != userId) return Result.InvalidIdentity

        storage.deleteMeeting(meetingIdentity.id)
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
