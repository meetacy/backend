package app.meetacy.backend.usecase.meetings

import app.meetacy.backend.domain.AccessToken
import app.meetacy.backend.domain.UserId
import app.meetacy.backend.usecase.types.AuthRepository
import app.meetacy.backend.usecase.types.FullMeeting
import app.meetacy.backend.usecase.types.MeetingView
import app.meetacy.backend.usecase.types.authorize

class GetMeetingsListUsecase(
    private val authRepository: AuthRepository,
    private val storage: Storage,
    private val viewMeetingsRepository: ViewMeetingsRepository
) {

    suspend fun getMeetingsList(accessToken: AccessToken): Result {
        val memberId = authRepository.authorize(accessToken) { return Result.TokenInvalid }
        val listMeetings = storage.getList(memberId)

        return Result.Success(viewMeetingsRepository.viewMeetings(memberId, listMeetings))
    }

    sealed interface Result {
        class Success(val meetings: List<MeetingView>) : Result
        object TokenInvalid : Result
    }

    interface Storage {
        fun getMeetingsList(memberId: UserId): List<MeetingId>
    }
}
