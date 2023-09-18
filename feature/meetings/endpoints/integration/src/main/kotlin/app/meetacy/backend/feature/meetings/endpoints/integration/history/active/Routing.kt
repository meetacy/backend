package app.meetacy.backend.feature.meetings.endpoints.integration.history.active

import app.meetacy.backend.feature.meetings.endpoints.history.active.ListMeetingsActiveResult
import app.meetacy.backend.feature.meetings.endpoints.history.active.ListMeetingsActiveRepository
import app.meetacy.backend.feature.meetings.endpoints.history.active.listMeetingsActive
import app.meetacy.backend.feature.meetings.usecase.history.active.ListMeetingsActiveUsecase
import app.meetacy.backend.feature.meetings.usecase.history.active.ListMeetingsActiveUsecase.Result
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

internal fun Route.listMeetingsActive(di: DI) {
    val listMeetingsActiveUsecase: ListMeetingsActiveUsecase by di.getting

    val repository = object : ListMeetingsActiveRepository {
        override suspend fun getList(
            accessIdentity: AccessIdentity,
            amount: Amount,
            pagingId: PagingId?
        ): ListMeetingsActiveResult = when (
            val result = listMeetingsActiveUsecase.getActiveMeetingsList(
                accessIdentity = accessIdentity.type(),
                amount = amount.type(),
                pagingId = pagingId?.type()
            )
        ) {
            Result.InvalidAccessIdentity -> ListMeetingsActiveResult.InvalidIdentity
            is Result.Success -> ListMeetingsActiveResult.Success(
                meetings = result.paging
                    .mapItems(MeetingView::serializable)
                    .serializable()
            )
        }
    }

    listMeetingsActive(repository)
}
