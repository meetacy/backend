package app.meetacy.backend.feature.meetings.endpoints.history.list

import app.meetacy.backend.endpoint.ktor.Failure
import app.meetacy.backend.endpoint.ktor.respondFailure
import app.meetacy.backend.endpoint.ktor.respondSuccess
import app.meetacy.backend.feature.meetings.endpoints.history.list.ListMeetingsHistoryResult.*
import app.meetacy.backend.types.paging.serializable.PagingId
import app.meetacy.backend.types.paging.serializable.PagingResult
import app.meetacy.backend.types.serializable.access.AccessIdentity
import app.meetacy.backend.types.serializable.amount.Amount
import app.meetacy.backend.types.serializable.meetings.Meeting
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable

@Serializable
data class ListParam(
    val token: AccessIdentity,
    val amount: Amount,
    val pagingId: PagingId? = null
)

sealed interface ListMeetingsHistoryResult {
    data class Success(val meetings: PagingResult<Meeting>) : ListMeetingsHistoryResult
    data object InvalidIdentity : ListMeetingsHistoryResult
}

interface ListMeetingsHistoryRepository {
    suspend fun getList(
        accessIdentity: AccessIdentity,
        amount: Amount,
        pagingId: PagingId?
    ): ListMeetingsHistoryResult
}

fun Route.listMeetingsHistory(repository: ListMeetingsHistoryRepository) = post("/list") {
    val params = call.receive<ListParam>()

    when (
        val result = repository.getList(
            accessIdentity = params.token,
            amount = params.amount,
            pagingId = params.pagingId
        )
    ) {
        is Success -> call.respondSuccess(result.meetings)
        is InvalidIdentity -> call.respondFailure(Failure.InvalidToken)
    }
}
