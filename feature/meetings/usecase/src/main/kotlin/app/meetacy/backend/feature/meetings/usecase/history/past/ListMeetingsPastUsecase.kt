package app.meetacy.backend.feature.meetings.usecase.history.past

import app.meetacy.backend.types.access.AccessIdentity
import app.meetacy.backend.types.amount.Amount
import app.meetacy.backend.types.auth.AuthRepository
import app.meetacy.backend.types.auth.authorizeWithUserId
import app.meetacy.backend.types.datetime.Date
import app.meetacy.backend.types.meetings.*
import app.meetacy.backend.types.paging.PagingId
import app.meetacy.backend.types.paging.PagingResult
import app.meetacy.backend.types.paging.PagingValue
import app.meetacy.backend.types.users.UserId
import kotlinx.coroutines.flow.*

class ListMeetingsPastUsecase(
    private val authRepository: AuthRepository,
    private val storage: Storage,
    private val getMeetingsViewsRepository: GetMeetingsViewsRepository
) {
    sealed interface Result {
        data class Success(val paging: PagingResult<MeetingView>) : Result
        data object InvalidAccessIdentity : Result
    }

    suspend fun getPastMeetingsList(
        accessIdentity: AccessIdentity,
        amount: Amount,
        pagingId: PagingId?
    ): Result {
        val userId = authRepository.authorizeWithUserId(accessIdentity) { return Result.InvalidAccessIdentity }

        val pagingValues = storage.getJoinHistoryFlow(userId = userId, startPagingId = pagingId).toList()
        val pagingValuesIterator = pagingValues.iterator()

        val list = getMeetingsViewsRepository.getMeetingsViews(userId, pagingValues.map { paging -> paging.value })
            .filter { meeting -> meeting.date < Date.today() }
            .take(amount.int)
            .map { meetingView -> meetingView to pagingValuesIterator.next().nextPagingId }
            .toList()

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
