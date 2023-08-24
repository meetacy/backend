package app.meetacy.backend.feature.meetings.usecase.integration.history.active

import app.meetacy.backend.feature.meetings.endpoints.history.active.ListMeetingsActiveResult
import app.meetacy.backend.feature.meetings.endpoints.history.active.ListMeetingsActiveRepository
import app.meetacy.backend.types.meetings.MeetingView
import app.meetacy.backend.types.paging.serializable.PagingId
import app.meetacy.backend.types.paging.serializable.serializable
import app.meetacy.backend.types.paging.serializable.type
import app.meetacy.backend.types.serializable.access.AccessIdentity
import app.meetacy.backend.types.serializable.access.type
import app.meetacy.backend.types.serializable.amount.Amount
import app.meetacy.backend.types.serializable.amount.type
import app.meetacy.backend.types.serializable.meetings.serializable
import app.meetacy.backend.feature.meetings.usecase.history.active.ListMeetingsActiveUsecase

class UsecaseListActiveMeetingsRepository(
    private val usecase: ListMeetingsActiveUsecase
): ListMeetingsActiveRepository {
    override suspend fun getList(
        accessIdentity: AccessIdentity,
        amount: Amount,
        pagingId: PagingId?
    ): ListMeetingsActiveResult = usecase.getActiveMeetingsList(
        accessIdentity.type(), amount.type(), pagingId?.type()
    ).mapToEndpoint()

    fun ListMeetingsActiveUsecase.Result.mapToEndpoint() = when (this) {
        is ListMeetingsActiveUsecase.Result.Success -> ListMeetingsActiveResult.Success(
            meetings = this.paging.map { it.map(MeetingView::serializable) }.serializable()
        )
        ListMeetingsActiveUsecase.Result.InvalidAccessIdentity -> ListMeetingsActiveResult.InvalidIdentity
    }
}
