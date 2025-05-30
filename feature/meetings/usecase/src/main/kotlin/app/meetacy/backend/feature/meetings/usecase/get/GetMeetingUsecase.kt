package app.meetacy.backend.feature.meetings.usecase.get

import app.meetacy.backend.types.access.AccessIdentity
import app.meetacy.backend.types.auth.AuthRepository
import app.meetacy.backend.types.auth.authorizeWithUserId
import app.meetacy.backend.types.meetings.GetMeetingsViewsRepository
import app.meetacy.backend.types.meetings.MeetingIdentity
import app.meetacy.backend.types.meetings.MeetingView
import app.meetacy.backend.types.meetings.getMeetingViewOrNull

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
