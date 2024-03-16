package app.meetacy.backend.feature.meetings.usecase.history.past

import app.meetacy.backend.types.access.AccessIdentity
import app.meetacy.backend.types.amount.Amount
import app.meetacy.backend.types.auth.AuthRepository
import app.meetacy.backend.types.auth.authorizeWithUserId
import app.meetacy.backend.types.datetime.Date
import app.meetacy.backend.types.datetime.meetacyDate
import app.meetacy.backend.types.meetings.GetMeetingsViewsRepository
import app.meetacy.backend.types.meetings.MeetingId
import app.meetacy.backend.types.meetings.MeetingView
import app.meetacy.backend.types.meetings.getMeetingsViews
import app.meetacy.backend.types.paging.PagingId
import app.meetacy.backend.types.paging.PagingResult
import app.meetacy.backend.types.paging.PagingValue
import app.meetacy.backend.types.paging.pagingResult
import app.meetacy.backend.types.users.UserId
import java.time.Instant
import java.time.ZoneOffset

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

        val now = Instant.now()
            .atOffset(ZoneOffset.UTC)
            .toLocalDate()
            .minusDays(1)

        val meetingIds = storage.getJoinHistoryFlowDescending(
            userId = userId,
            amount = amount,
            startPagingId = pagingId,
            before = now.plusDays(1).meetacyDate
        )

        val meetings = getMeetingsViewsRepository.getMeetingsViews(
            viewerId = userId,
            meetingIds = meetingIds.map { (meetingId) -> meetingId }
        ).iterator()

        val paging = meetingIds
            .pagingResult(amount)
            .mapItems { meetings.next() }

        return Result.Success(paging)
    }

    interface Storage {
        suspend fun getJoinHistoryFlowDescending(
            userId: UserId,
            amount: Amount,
            startPagingId: PagingId?,
            before: Date
        ): List<PagingValue<MeetingId>>
    }
}
