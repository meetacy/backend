package app.meetacy.backend.feature.meetings.usecase.integration.history.past

import app.meetacy.backend.feature.meetings.endpoints.history.list.ListMeetingsResult
import app.meetacy.backend.feature.meetings.endpoints.history.past.ListMeetingsPastRepository
import app.meetacy.backend.types.meetings.MeetingView
import app.meetacy.backend.types.paging.serializable.PagingId
import app.meetacy.backend.types.paging.serializable.serializable
import app.meetacy.backend.types.paging.serializable.type
import app.meetacy.backend.types.serializable.access.AccessIdentity
import app.meetacy.backend.types.serializable.access.type
import app.meetacy.backend.types.serializable.amount.Amount
import app.meetacy.backend.types.serializable.amount.type
import app.meetacy.backend.types.serializable.meetings.serializable
import app.meetacy.backend.feature.meetings.usecase.history.past.ListMeetingsPastUsecase

class UsecaseListPastMeetingsRepository(
    private val usecase: ListMeetingsPastUsecase
): ListMeetingsPastRepository {
    override suspend fun getList(
        accessIdentity: AccessIdentity,
        amount: Amount,
        pagingId: PagingId?
    ): ListMeetingsResult = usecase.getPastMeetingsList(
        accessIdentity.type(), amount.type(), pagingId?.type()
    ).mapToEndpoint()

    fun ListMeetingsPastUsecase.Result.mapToEndpoint() = when (this) {
        is ListMeetingsPastUsecase.Result.Success -> ListMeetingsResult.Success(
            meetings = paging.map { it.map(MeetingView::serializable) }.serializable()
        )
        ListMeetingsPastUsecase.Result.InvalidAccessIdentity -> ListMeetingsResult.InvalidIdentity
    }
}
