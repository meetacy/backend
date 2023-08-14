package app.meetacy.backend.feature.auth.usecase.integration.meetings.history.list

import app.meetacy.backend.endpoint.meetings.history.list.ListMeetingsHistoryRepository
import app.meetacy.backend.endpoint.meetings.history.list.ListMeetingsResult
import app.meetacy.backend.feature.auth.usecase.integration.types.mapToEndpoint
import app.meetacy.backend.feature.auth.usecase.meetings.history.list.ListMeetingsHistoryUsecase
import app.meetacy.backend.types.access.AccessIdentity
import app.meetacy.backend.types.amount.Amount
import app.meetacy.backend.types.paging.PagingId

class UsecaseListMeetingsHistoryRepository(
    private val usecase: ListMeetingsHistoryUsecase
): ListMeetingsHistoryRepository {
    override suspend fun getList(
        accessIdentity: AccessIdentity,
        amount: Amount,
        pagingId: PagingId?
    ): ListMeetingsResult = usecase.getMeetingsList(accessIdentity, amount, pagingId).toEndpoint()

    private fun ListMeetingsHistoryUsecase.Result.toEndpoint(): ListMeetingsResult = when (this) {
        ListMeetingsHistoryUsecase.Result.InvalidAccessIdentity -> ListMeetingsResult.InvalidIdentity
        is ListMeetingsHistoryUsecase.Result.Success -> ListMeetingsResult.Success(
            meetings = this.paging.map { meetings ->
                meetings.map { meeting ->
                    meeting.mapToEndpoint()
                }
            }
        )
    }
}
