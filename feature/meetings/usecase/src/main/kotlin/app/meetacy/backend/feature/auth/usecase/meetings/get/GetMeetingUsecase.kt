package app.meetacy.backend.feature.auth.usecase.meetings.get

import app.meetacy.backend.feature.auth.usecase.types.*
import app.meetacy.backend.types.access.AccessIdentity
import app.meetacy.backend.types.meeting.MeetingIdentity

class GetMeetingUsecase(
    private val authRepository: AuthRepository,
    private val getMeetingsViewsRepository: GetMeetingsViewsRepository
) {
    suspend fun getMeeting(
        accessIdentity: AccessIdentity,
        meetingIdentity: MeetingIdentity
    ): Result {
        val userId = authRepository.authorizeWithUserId(accessIdentity) { return Result.TokenInvalid }

        val meeting = getMeetingsViewsRepository
            .getMeetingViewOrNull(userId, meetingIdentity)
            ?: return Result.MeetingNotFound

        return Result.Success(meeting)
    }

    sealed interface Result {
        class Success(val meeting: MeetingView) : Result
        data object TokenInvalid : Result
        data object MeetingNotFound : Result
    }
}
