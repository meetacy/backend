package app.meetacy.backend.feature.meetings.endpoints.integration.history.list

import app.meetacy.backend.feature.meetings.endpoints.history.list.ListMeetingsHistoryRepository
import app.meetacy.backend.feature.meetings.endpoints.history.list.ListMeetingsHistoryResult
import app.meetacy.backend.feature.meetings.endpoints.history.list.listMeetingsHistory
import app.meetacy.backend.feature.meetings.usecase.history.list.ListMeetingsHistoryUsecase
import app.meetacy.backend.feature.meetings.usecase.history.list.ListMeetingsHistoryUsecase.Result
import app.meetacy.backend.types.meetings.MeetingView
import app.meetacy.backend.types.paging.serializable.PagingId
import app.meetacy.backend.types.paging.serializable.serializable
import app.meetacy.backend.types.paging.serializable.type
import app.meetacy.backend.types.serializable.access.AccessIdentity
import app.meetacy.backend.types.serializable.access.type
import app.meetacy.backend.types.serializable.amount.Amount
import app.meetacy.backend.types.serializable.amount.type
import app.meetacy.backend.types.serializable.meetings.serializable
import app.meetacy.di.DI
import io.ktor.server.routing.*

internal fun Route.listMeetingsHistory(di: DI) {
    val listMeetingsHistoryUsecase: ListMeetingsHistoryUsecase by di.getting

    val repository = object : ListMeetingsHistoryRepository {
        override suspend fun getList(
            accessIdentity: AccessIdentity,
            amount: Amount,
            pagingId: PagingId?
        ): ListMeetingsHistoryResult = when (
            val result = listMeetingsHistoryUsecase.getMeetingsList(
                accessIdentity = accessIdentity.type(),
                amount = amount.type(),
                pagingId = pagingId?.type()
            )
        ) {
            Result.InvalidAccessIdentity -> ListMeetingsHistoryResult.InvalidIdentity
            is Result.Success -> ListMeetingsHistoryResult.Success(
                meetings = result.paging
                    .mapItems(MeetingView::serializable)
                    .serializable()
            )
        }
    }

    listMeetingsHistory(repository)
}
