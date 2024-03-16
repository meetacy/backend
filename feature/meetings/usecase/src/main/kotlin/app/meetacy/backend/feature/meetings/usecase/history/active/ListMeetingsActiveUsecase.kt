package app.meetacy.backend.feature.meetings.usecase.history.active

import app.meetacy.backend.stdlib.flow.chunked
import app.meetacy.backend.types.access.AccessIdentity
import app.meetacy.backend.types.amount.Amount
import app.meetacy.backend.types.amount.amount
import app.meetacy.backend.types.auth.AuthRepository
import app.meetacy.backend.types.auth.authorizeWithUserId
import app.meetacy.backend.types.meetings.GetMeetingsViewsRepository
import app.meetacy.backend.types.meetings.MeetingId
import app.meetacy.backend.types.meetings.MeetingView
import app.meetacy.backend.types.meetings.getMeetingsViews
import app.meetacy.backend.types.paging.PagingId
import app.meetacy.backend.types.paging.PagingResult
import app.meetacy.backend.types.paging.PagingValue
import app.meetacy.backend.types.paging.pagingResult
import app.meetacy.backend.types.users.UserId
import kotlinx.coroutines.flow.*
import java.time.Instant
import java.time.ZoneOffset

class ListMeetingsActiveUsecase(
    private val authRepository: AuthRepository,
    private val storage: Storage,
    private val getMeetingsViewsRepository: GetMeetingsViewsRepository
) {
    sealed interface Result {
        class Success(val paging: PagingResult<MeetingView>) : Result
        data object InvalidAccessIdentity : Result
    }

    suspend fun getActiveMeetingsList(
        accessIdentity: AccessIdentity,
        amount: Amount,
        pagingId: PagingId?,
        chunkSize: Amount = 100.amount,
    ): Result {
        val userId = authRepository.authorizeWithUserId(accessIdentity) { return Result.InvalidAccessIdentity }

        val history = storage.getJoinHistoryFlow(userId = userId, startPagingId = pagingId)

        val now = Instant.now()
            .atOffset(ZoneOffset.UTC)
            .toLocalDate()
            .minusDays(1)

        val meetings = history.chunked(chunkSize.int) { meetingIds ->
            val views = getMeetingsViewsRepository.getMeetingsViews(
                viewerId = userId,
                meetingIds = meetingIds.map { (meetingId) -> meetingId }
            ).iterator()

            meetingIds.map { paging -> paging.map { views.next() } }
        }
            .transform { meetings -> emitAll(meetings.asFlow()) }
            .takeWhile { view -> view.value.date.javaLocalDate >= now }
            .take(amount.int)
            .toList()

        val paging = meetings.pagingResult(amount)

        return Result.Success(paging)
    }

    interface Storage {
        suspend fun getJoinHistoryFlow(
            userId: UserId,
            startPagingId: PagingId?
        ): Flow<PagingValue<MeetingId>>
    }
}
