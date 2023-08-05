package app.meetacy.backend.usecase.integration.meetings.history.active

import app.meetacy.backend.endpoint.meetings.history.active.ListMeetingsActiveRepository
import app.meetacy.backend.endpoint.meetings.history.list.ListMeetingsResult
import app.meetacy.backend.types.access.AccessIdentity
import app.meetacy.backend.types.amount.Amount
import app.meetacy.backend.types.paging.PagingId
import app.meetacy.backend.usecase.integration.types.mapToEndpoint
import app.meetacy.backend.usecase.meetings.history.active.ListMeetingsActiveUsecase

class UsecaseListActiveMeetingsRepository(
    private val usecase: ListMeetingsActiveUsecase
): ListMeetingsActiveRepository {
    override suspend fun getList(
        accessIdentity: AccessIdentity,
        amount: Amount,
        pagingId: PagingId?
    ): ListMeetingsResult = usecase.getActiveMeetingsList(
        accessIdentity, amount, pagingId
    ).mapToEndpoint()

    fun ListMeetingsActiveUsecase.Result.mapToEndpoint() = when (this) {
        is ListMeetingsActiveUsecase.Result.Success -> ListMeetingsResult.Success(
            meetings = paging.map { meetingViews ->
                meetingViews.map { it.mapToEndpoint() }
            }
        )
        ListMeetingsActiveUsecase.Result.InvalidAccessIdentity -> ListMeetingsResult.InvalidIdentity
    }
}
