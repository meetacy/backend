package app.meetacy.backend.usecase.integration.meetings.history.list

import app.meetacy.backend.endpoint.meetings.history.list.ListMeetingsResult
import app.meetacy.backend.endpoint.meetings.history.list.ListMeetingsHistoryRepository
import app.meetacy.backend.types.access.AccessIdentity
import app.meetacy.backend.types.amount.Amount
import app.meetacy.backend.types.paging.PagingId
import app.meetacy.backend.usecase.integration.types.mapToEndpoint
import app.meetacy.backend.usecase.meetings.history.list.ListMeetingsHistoryUsecase
import app.meetacy.backend.usecase.meetings.history.list.ListMeetingsHistoryUsecase.Result

class UsecaseListMeetingsHistoryRepository(
    private val usecase: ListMeetingsHistoryUsecase
): ListMeetingsHistoryRepository {
    override suspend fun getList(
        accessIdentity: AccessIdentity,
        amount: Amount,
        pagingId: PagingId?
    ): ListMeetingsResult = when (
        val result = usecase.getMeetingsList(accessIdentity, amount, pagingId)
    ) {
        is Result.Success -> ListMeetingsResult.Success(
            meetings = result.paging.map { meetings ->
                meetings.map { meeting ->
                    meeting.mapToEndpoint()
                }
            }
        )
        is Result.InvalidAccessIdentity -> ListMeetingsResult.InvalidIdentity
    }
}
