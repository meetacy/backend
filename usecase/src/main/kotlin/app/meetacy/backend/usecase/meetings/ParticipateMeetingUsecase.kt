package app.meetacy.backend.usecase.meetings

import app.meetacy.backend.types.AccessHash
import app.meetacy.backend.types.AccessToken
import app.meetacy.backend.types.MeetingId
import app.meetacy.backend.types.UserId
import app.meetacy.backend.usecase.types.AuthRepository
import app.meetacy.backend.usecase.types.GetMeetingsViewsRepository
import app.meetacy.backend.usecase.types.authorize

class ParticipateMeetingUsecase(
    private val authRepository: AuthRepository,
    private val storage: Storage,
    private val getMeetingsViewsRepository: GetMeetingsViewsRepository
) {

    suspend fun participateMeeting(
        meetingId: MeetingId,
        meetingAccessHash: AccessHash,
        accessToken: AccessToken
    ): Result {
        val userId = authRepository.authorize(accessToken) { return Result.TokenInvalid }

        val meeting = getMeetingsViewsRepository
            .getMeetingsViewsOrNull(userId, listOf(meetingId))
            .first()
            ?: return Result.MeetingNotFound

        if (meetingAccessHash != meeting.accessHash)
            return Result.MeetingNotFound

        storage.addParticipant(meetingId, userId)

        return Result.Success
    }

    sealed interface Result {
        object Success : Result
        object TokenInvalid : Result
        object MeetingNotFound : Result
    }

    interface Storage {
        suspend fun addParticipant(
            meetingId: MeetingId,
            userId: UserId
        )
    }
}
