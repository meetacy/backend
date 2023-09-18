package app.meetacy.backend.feature.meetings.endpoints.integration.history.past

import app.meetacy.backend.feature.meetings.endpoints.history.past.ListMeetingsPastRepository
import app.meetacy.backend.feature.meetings.endpoints.history.past.ListMeetingsPastResult
import app.meetacy.backend.feature.meetings.endpoints.history.past.listMeetingsPast
import app.meetacy.backend.feature.meetings.usecase.history.past.ListMeetingsPastUsecase
import app.meetacy.backend.feature.meetings.usecase.history.past.ListMeetingsPastUsecase.Result
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

fun Route.listMeetingsPast(di: DI) {
    val listMeetingsPastUsecase: ListMeetingsPastUsecase by di.getting

    val repository = object : ListMeetingsPastRepository {
        override suspend fun getList(
            accessIdentity: AccessIdentity,
            amount: Amount,
            pagingId: PagingId?
        ): ListMeetingsPastResult = when (
            val result = listMeetingsPastUsecase.getPastMeetingsList(
                accessIdentity = accessIdentity.type(),
                amount = amount.type(),
                pagingId = pagingId?.type()
            )
        ) {
            Result.InvalidAccessIdentity -> ListMeetingsPastResult.InvalidIdentity
            is Result.Success -> ListMeetingsPastResult.Success(
                meetings = result.paging
                    .mapItems(MeetingView::serializable)
                    .serializable()
            )
        }
    }

    listMeetingsPast(repository)
}
