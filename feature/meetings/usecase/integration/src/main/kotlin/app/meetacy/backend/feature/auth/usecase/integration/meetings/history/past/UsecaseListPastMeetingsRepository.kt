package app.meetacy.backend.feature.auth.usecase.integration.meetings.history.past

import app.meetacy.backend.endpoint.meetings.history.list.ListMeetingsResult
import app.meetacy.backend.endpoint.meetings.history.past.ListMeetingsPastRepository
import app.meetacy.backend.types.access.AccessIdentity
import app.meetacy.backend.types.amount.Amount
import app.meetacy.backend.types.paging.PagingId
import app.meetacy.backend.usecase.integration.types.mapToEndpoint
import app.meetacy.backend.usecase.meetings.history.past.ListMeetingsPastUsecase

class UsecaseListPastMeetingsRepository(
    private val usecase: ListMeetingsPastUsecase
): ListMeetingsPastRepository {
    override suspend fun getList(
        accessIdentity: AccessIdentity,
        amount: Amount,
        pagingId: PagingId?
    ): ListMeetingsResult = usecase.getPastMeetingsList(
        accessIdentity, amount, pagingId
    ).mapToEndpoint()

    fun ListMeetingsPastUsecase.Result.mapToEndpoint() = when (this) {
        is ListMeetingsPastUsecase.Result.Success -> ListMeetingsResult.Success(
            meetings = paging.map { meetingViews ->
                meetingViews.map { it.mapToEndpoint() }
            }
        )
        ListMeetingsPastUsecase.Result.InvalidAccessIdentity -> ListMeetingsResult.InvalidIdentity
    }
}
