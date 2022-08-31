package app.meetacy.backend.usecase.meetings

import app.meetacy.backend.types.AccessIdentity
import app.meetacy.backend.types.MeetingId
import app.meetacy.backend.types.UserId
import app.meetacy.backend.usecase.types.*

class GetMeetingsListUsecase(
    private val authRepository: AuthRepository,
    private val storage: Storage,
    private val getMeetingsViewsRepository: GetMeetingsViewsRepository
) {

    suspend fun getMeetingsList(accessIdentity: AccessIdentity): Result {
        val id = authRepository.authorizeWithUserId(accessIdentity) { return Result.TokenInvalid }

        val meetingIds = storage.getSelfMeetings(id) + storage.getParticipatingMeetings(id)
        val meetings = getMeetingsViewsRepository.getMeetingsViews(id, meetingIds)

        return Result.Success(meetings)
    }

    sealed interface Result {
        class Success(val meetings: List<MeetingView>) : Result
        object TokenInvalid : Result
    }

    interface Storage {
        suspend fun getSelfMeetings(creatorId: UserId): List<MeetingId>
        suspend fun getParticipatingMeetings(memberId: UserId): List<MeetingId>
    }
}
