package app.meetacy.backend.feature.auth.usecase.meetings.get

import app.meetacy.backend.types.access.AccessIdentity
import app.meetacy.backend.types.meeting.MeetingIdentity
import app.meetacy.backend.usecase.types.*

class GetMeetingUsecase(
    private val authRepository: app.meetacy.backend.feature.auth.usecase.types.AuthRepository,
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
        object TokenInvalid : Result
        object MeetingNotFound : Result
    }
}
