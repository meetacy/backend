package app.meetacy.backend.usecase.meetings

import app.meetacy.backend.types.AccessHash
import app.meetacy.backend.types.AccessToken
import app.meetacy.backend.types.MeetingId
import app.meetacy.backend.types.MeetingIdentity
import app.meetacy.backend.usecase.types.AuthRepository
import app.meetacy.backend.usecase.types.GetMeetingsViewsRepository
import app.meetacy.backend.usecase.types.MeetingView
import app.meetacy.backend.usecase.types.authorize

class GetMeetingUsecase(
    private val authRepository: AuthRepository,
    private val getMeetingsViewsRepository: GetMeetingsViewsRepository
) {
    suspend fun getMeeting(
        accessToken: AccessToken,
        meetingIdentity: MeetingIdentity
    ): Result {
        val userId = authRepository.authorize(accessToken) { return Result.TokenInvalid }

        val meeting = getMeetingsViewsRepository
            .getMeetingsViewsOrNull(userId, listOf(meetingIdentity.meetingId))
            .first()
            ?: return Result.MeetingNotFound

        if (meetingIdentity.accessHash != meeting.identity.accessHash)
            return Result.MeetingNotFound

        return Result.Success(meeting)
    }

    sealed interface Result {
        class Success(val meeting: MeetingView) : Result
        object TokenInvalid : Result
        object MeetingNotFound : Result
    }
}
