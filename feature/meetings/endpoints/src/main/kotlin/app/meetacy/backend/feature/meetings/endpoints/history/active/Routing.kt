package app.meetacy.backend.feature.meetings.endpoints.history.active

import app.meetacy.backend.core.endpoints.accessIdentity
import app.meetacy.backend.core.endpoints.amountOrNull
import app.meetacy.backend.core.endpoints.pagingIdOrNull
import app.meetacy.backend.endpoint.ktor.Failure
import app.meetacy.backend.endpoint.ktor.respondFailure
import app.meetacy.backend.endpoint.ktor.respondSuccess
import app.meetacy.backend.feature.meetings.endpoints.history.active.ListMeetingsActiveResult.InvalidIdentity
import app.meetacy.backend.feature.meetings.endpoints.history.active.ListMeetingsActiveResult.Success
import app.meetacy.backend.types.paging.serializable.PagingId
import app.meetacy.backend.types.paging.serializable.PagingResult
import app.meetacy.backend.types.serializable.access.AccessIdentity
import app.meetacy.backend.types.serializable.amount.Amount
import app.meetacy.backend.types.serializable.meetings.Meeting
import io.ktor.server.application.*
import io.ktor.server.routing.*
import kotlinx.serialization.SerializationException

sealed interface ListMeetingsActiveResult {
    data class Success(val meetings: PagingResult<Meeting>) : ListMeetingsActiveResult
    data object InvalidIdentity : ListMeetingsActiveResult
}

interface ListMeetingsActiveRepository {
    suspend fun getList(
        accessIdentity: AccessIdentity,
        amount: Amount,
        pagingId: PagingId?
    ): ListMeetingsActiveResult
}

fun Route.listMeetingsActive(repository: ListMeetingsActiveRepository) = get("/active") {
    val amount = call.parameters.amountOrNull() ?: throw SerializationException("Bad request. Illegal input: param 'amount' is required for type with serial name, but it was missing at path: $")
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
