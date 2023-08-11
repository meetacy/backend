package app.meetacy.backend.usecase.integration.meetings.history.list

import app.meetacy.backend.endpoint.meetings.history.list.ListMeetingsHistoryRepository
import app.meetacy.backend.endpoint.meetings.history.list.ListMeetingsResult
import app.meetacy.backend.types.paging.serializable.PagingId
import app.meetacy.backend.types.paging.serializable.serializable
import app.meetacy.backend.types.paging.serializable.type
import app.meetacy.backend.types.serializable.access.AccessIdentity
import app.meetacy.backend.types.serializable.access.type
import app.meetacy.backend.types.serializable.amount.Amount
import app.meetacy.backend.types.serializable.amount.type
import app.meetacy.backend.types.serializable.meetings.type
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
        val result = usecase.getMeetingsList(accessIdentity.type(), amount.type(), pagingId?.type())
    ) {
        is Result.Success -> ListMeetingsResult.Success(
            meetings = result.paging.map { meetings ->
                meetings.map { meeting ->
                    meeting.type()
                }
            }.serializable()
        )
        is Result.InvalidAccessIdentity -> ListMeetingsResult.InvalidIdentity
    }
}
