package app.meetacy.backend.usecase.meetings.history.list

import app.meetacy.backend.types.*
import app.meetacy.backend.usecase.types.*

class ListMeetingsHistoryUsecase(
    private val authRepository: AuthRepository,
    private val storage: Storage,
    private val getMeetingsViewsRepository: GetMeetingsViewsRepository
) {

    suspend fun getMeetingsList(
        accessIdentity: AccessIdentity,
        amount: Amount,
        pagingId: PagingId?
    ): Result {
        val id = authRepository.authorizeWithUserId(accessIdentity) { return Result.TokenInvalid }

        val meetingIds = storage.getParticipatingMeetings(
            memberId = id,
            amount = amount,
            lastMeetingId = pagingId?.long?.let(::MeetingId)
        )
        val meetings = getMeetingsViewsRepository.getMeetingsViews(id, meetingIds)

        return Result.Success(
            paging = PagingResult(
                nextPagingId = if (meetings.size == amount.int) PagingId(meetings.last().id.long) else null,
                data = meetings
            )
        )
    }

    sealed interface Result {
        class Success(val paging: PagingResult<List<MeetingView>>) : Result
        object TokenInvalid : Result
    }

    interface Storage {
        suspend fun getParticipatingMeetings(
            memberId: UserId,
            amount: Amount,
            lastMeetingId: MeetingId?
        ): List<MeetingId>
    }
}