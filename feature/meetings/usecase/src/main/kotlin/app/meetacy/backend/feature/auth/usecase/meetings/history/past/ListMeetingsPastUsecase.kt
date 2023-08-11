package app.meetacy.backend.feature.auth.usecase.meetings.history.past

import app.meetacy.backend.types.access.AccessIdentity
import app.meetacy.backend.types.amount.Amount
import app.meetacy.backend.types.datetime.Date
import app.meetacy.backend.types.meeting.MeetingId
import app.meetacy.backend.types.paging.PagingId
import app.meetacy.backend.types.paging.PagingResult
import app.meetacy.backend.types.paging.PagingValue
import app.meetacy.backend.types.user.UserId
import app.meetacy.backend.usecase.types.*
import kotlinx.coroutines.flow.*

class ListMeetingsPastUsecase(
    private val authRepository: AuthRepository,
    private val storage: Storage,
    private val getMeetingsViewsRepository: GetMeetingsViewsRepository
) {
    sealed interface Result {
        class Success(val paging: PagingResult<MeetingView>) : Result
        object InvalidAccessIdentity : Result
    }

    suspend fun getPastMeetingsList(
        accessIdentity: AccessIdentity,
        amount: Amount,
        pagingId: PagingId?
    ): Result {
        val userId = authRepository.authorizeWithUserId(accessIdentity) { return Result.InvalidAccessIdentity }

        val list = storage.getJoinHistoryFlow(
            userId = userId,
            startPagingId = pagingId
        ).map { (meetingId, nextPagingId) ->
            getMeetingsViewsRepository.getMeetingView(userId, meetingId) to nextPagingId
        }.filter { (meeting) ->
            meeting.date < Date.today()
        }.take(amount.int).toList()

        val nextPagingId = if (list.size == amount.int) list.last().second else null

        val paging = PagingResult(
            data = list.map { (meeting) -> meeting },
            nextPagingId = nextPagingId
        )

        return Result.Success(paging)
    }

    interface Storage {
        suspend fun getJoinHistoryFlow(
            userId: UserId,
            startPagingId: PagingId?
        ): Flow<PagingValue<MeetingId>>
    }
}
