package app.meetacy.backend.usecase.meetings.history.active

import app.meetacy.backend.types.access.AccessIdentity
import app.meetacy.backend.types.amount.Amount
import app.meetacy.backend.types.auth.AuthRepository
import app.meetacy.backend.types.auth.authorizeWithUserId
import app.meetacy.backend.types.datetime.Date
import app.meetacy.backend.types.meetings.GetMeetingsViewsRepository
import app.meetacy.backend.types.meetings.MeetingId
import app.meetacy.backend.types.meetings.MeetingView
import app.meetacy.backend.types.meetings.getMeetingView
import app.meetacy.backend.types.paging.PagingId
import app.meetacy.backend.types.paging.PagingResult
import app.meetacy.backend.types.paging.PagingValue
import app.meetacy.backend.types.paging.pagingId
import app.meetacy.backend.types.users.UserId
import kotlinx.coroutines.flow.*

class ListMeetingsActiveUsecase(
    private val authRepository: AuthRepository,
    private val storage: Storage,
    private val getMeetingsViewsRepository: GetMeetingsViewsRepository
) {
    sealed interface Result {
        class Success(val paging: PagingResult<MeetingView>) : Result
        object InvalidAccessIdentity : Result
    }

    suspend fun getActiveMeetingsList(
        accessIdentity: AccessIdentity,
        amount: Amount,
        pagingId: PagingId?
    ): Result {
        val userId = authRepository.authorizeWithUserId(accessIdentity) { return Result.InvalidAccessIdentity }

        val list = storage
            .getJoinHistoryFlow(
                userId = userId,
                startPagingId = pagingId
            ).map { meetingIdPaging ->
                meetingIdPaging.map { meetingId ->
                    getMeetingsViewsRepository.getMeetingView(
                        viewerId = userId,
                        meetingId = meetingId
                    )
                }
            }.filter { (meeting) -> meeting.date >= Date.today() }
            .take(amount.int).toList()

        val paging = PagingResult(
            data = list.map { (meeting) -> meeting },
            nextPagingId = list.pagingId(amount) { it.nextPagingId }
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
