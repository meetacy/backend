package app.meetacy.backend.feature.meetings.endpoints.history.active

import app.meetacy.backend.endpoint.ktor.Failure
import app.meetacy.backend.endpoint.ktor.respondFailure
import app.meetacy.backend.endpoint.ktor.respondSuccess
import app.meetacy.backend.feature.meetings.endpoints.history.active.ListMeetingsActiveResult.*
import app.meetacy.backend.feature.meetings.endpoints.history.list.ListParam
import app.meetacy.backend.types.paging.serializable.PagingId
import app.meetacy.backend.types.paging.serializable.PagingResult
import app.meetacy.backend.types.serializable.access.AccessIdentity
import app.meetacy.backend.types.serializable.amount.Amount
import app.meetacy.backend.types.serializable.meetings.Meeting
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.routing.*

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
    val params = call.receive<ListParam>()

    when (
        val result = repository.getList(
            accessIdentity = params.token,
            amount = params.amount,
            pagingId = params.pagingId
        )
    ) {
        is InvalidIdentity -> call.respondFailure(Failure.InvalidToken)
        is Success -> call.respondSuccess(result.meetings)
    }
}
