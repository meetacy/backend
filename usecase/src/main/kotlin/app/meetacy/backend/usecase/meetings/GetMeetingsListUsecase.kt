package app.meetacy.backend.usecase.meetings

import app.meetacy.backend.types.AccessToken
import app.meetacy.backend.types.MeetingId
import app.meetacy.backend.types.UserId
import app.meetacy.backend.usecase.types.*

class GetMeetingsListUsecase(
    private val authRepository: AuthRepository,
    private val storage: Storage,
    private val getMeetingsViewsRepository: GetMeetingsViewsRepository
) {

    suspend fun getMeetingsList(accessToken: AccessToken): Result {
        val memberId = authRepository.authorize(accessToken) { return Result.TokenInvalid }

        val meetingIds = storage.getMeetingsList(memberId)
        val meetings = getMeetingsViewsRepository.getMeetingsViews(memberId, meetingIds)

        return Result.Success(meetings)
    }

    sealed interface Result {
        class Success(val meetings: List<MeetingView>) : Result
        object TokenInvalid : Result
    }

    interface Storage {
        fun getMeetingsList(memberId: UserId): List<MeetingId>
    }
}
