package app.meetacy.backend.usecase.meetings

import app.meetacy.backend.types.AccessHash
import app.meetacy.backend.types.AccessToken
import app.meetacy.backend.types.MeetingId
import app.meetacy.backend.usecase.types.*

class GetMeetingUsecase(
    private val authRepository: AuthRepository,
    private val getMeetingsViewsRepository: GetMeetingsViewsRepository
) {
    suspend fun getMeeting(
        accessToken: AccessToken,
        meetingId: MeetingId,
        meetingAccessHash: AccessHash
    ): Result {
        val userId = authRepository.authorize(accessToken) { return Result.TokenInvalid }
        val listMeeting = listOf(meetingId)
        val meeting = getMeetingsViewsRepository.getMeetingsViewsOrNull(userId, listMeeting)
        return if (meeting.first() != null) {
            Result.Success(meeting.first()!!)
        } else Result.MeetingNotFound
    }

    sealed interface Result {
        class Success(val meeting: MeetingView) : Result
        object TokenInvalid : Result
        object MeetingNotFound : Result
    }
}
