package app.meetacy.backend.feature.meetings.endpoints.history.past

import app.meetacy.backend.core.endpoints.accessIdentity
import app.meetacy.backend.core.endpoints.amount
import app.meetacy.backend.core.endpoints.pagingIdOrNull
import app.meetacy.backend.endpoint.ktor.Failure
import app.meetacy.backend.endpoint.ktor.respondFailure
import app.meetacy.backend.endpoint.ktor.respondSuccess
import app.meetacy.backend.feature.meetings.endpoints.history.past.ListMeetingsPastResult.InvalidIdentity
import app.meetacy.backend.feature.meetings.endpoints.history.past.ListMeetingsPastResult.Success
import app.meetacy.backend.types.paging.serializable.PagingId
import app.meetacy.backend.types.paging.serializable.PagingResult
import app.meetacy.backend.types.serializable.access.AccessIdentity
import app.meetacy.backend.types.serializable.amount.Amount
import app.meetacy.backend.types.serializable.meetings.Meeting
import io.ktor.server.application.*
import io.ktor.server.routing.*

sealed interface ListMeetingsPastResult {
    data class Success(val meetings: PagingResult<Meeting>) : ListMeetingsPastResult
    data object InvalidIdentity : ListMeetingsPastResult
}

interface ListMeetingsPastRepository {
    suspend fun getList(
        accessIdentity: AccessIdentity,
        amount: Amount,
        pagingId: PagingId?
    ): ListMeetingsPastResult
}

fun Route.listMeetingsPast(repository: ListMeetingsPastRepository) = get("/past") {
    val amount = call.parameters.amount()
    val pagingId = call.parameters.pagingIdOrNull()
    val token = call.accessIdentity()

    when (
        val result = repository.getList(
            accessIdentity = token,
            amount = amount,
            pagingId = pagingId
        )
    ) {
        is InvalidIdentity -> call.respondFailure(Failure.InvalidToken)
        is Success -> call.respondSuccess(result.meetings)
    }
}
