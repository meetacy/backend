package app.meetacy.backend.feature.meetings.usecase.integration.history.active

import app.meetacy.backend.feature.meetings.endpoints.history.active.ListMeetingsActiveRepository
import app.meetacy.backend.feature.meetings.endpoints.history.list.ListMeetingsResult
import app.meetacy.backend.types.meetings.MeetingView
import app.meetacy.backend.types.paging.serializable.PagingId
import app.meetacy.backend.types.paging.serializable.serializable
import app.meetacy.backend.types.paging.serializable.type
import app.meetacy.backend.types.serializable.access.AccessIdentity
import app.meetacy.backend.types.serializable.access.type
import app.meetacy.backend.types.serializable.amount.Amount
import app.meetacy.backend.types.serializable.amount.type
import app.meetacy.backend.types.serializable.meetings.type
import app.meetacy.backend.feature.meetings.usecase.history.active.ListMeetingsActiveUsecase

class UsecaseListActiveMeetingsRepository(
    private val usecase: ListMeetingsActiveUsecase
): ListMeetingsActiveRepository {
    override suspend fun getList(
        accessIdentity: AccessIdentity,
        amount: Amount,
        pagingId: PagingId?
    ): ListMeetingsResult = usecase.getActiveMeetingsList(
        accessIdentity.type(), amount.type(), pagingId?.type()
    ).mapToEndpoint()

    fun ListMeetingsActiveUsecase.Result.mapToEndpoint() = when (this) {
        is ListMeetingsActiveUsecase.Result.Success -> ListMeetingsResult.Success(
            meetings = this.paging.map { it.map(MeetingView::type) }.serializable()
        )
        ListMeetingsActiveUsecase.Result.InvalidAccessIdentity -> ListMeetingsResult.InvalidIdentity
    }
}
