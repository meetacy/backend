package app.meetacy.backend.usecase.meetings.history.list

import app.meetacy.backend.types.access.AccessIdentity
import app.meetacy.backend.types.amount.Amount
import app.meetacy.backend.types.meeting.IdMeeting
import app.meetacy.backend.types.paging.PagingId
import app.meetacy.backend.types.paging.PagingResult
import app.meetacy.backend.types.user.UserId
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
        val id = authRepository.authorizeWithUserId(accessIdentity) { return Result.InvalidAccessIdentity }

        val paging = storage.getParticipatingMeetings(
            memberId = id,
            amount = amount,
            pagingId = pagingId
        ).map { meetingIds ->
            getMeetingsViewsRepository.getMeetingsViews(id, meetingIds)
        }

        return Result.Success(paging)
    }

    sealed interface Result {
        class Success(val paging: PagingResult<List<MeetingView>>) : Result
        object InvalidAccessIdentity : Result
    }

    interface Storage {
        suspend fun getParticipatingMeetings(
            memberId: UserId,
            amount: Amount,
            pagingId: PagingId?
        ): PagingResult<List<IdMeeting>>
    }
}