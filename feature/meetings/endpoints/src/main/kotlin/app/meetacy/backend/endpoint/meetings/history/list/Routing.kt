package app.meetacy.backend.endpoint.meetings.history.list

import app.meetacy.backend.endpoint.ktor.Failure
import app.meetacy.backend.endpoint.ktor.respondFailure
import app.meetacy.backend.endpoint.ktor.respondSuccess
import app.meetacy.backend.types.paging.serializable.PagingId
import app.meetacy.backend.types.paging.serializable.PagingResult
import app.meetacy.backend.types.serializable.access.AccessIdentity
import app.meetacy.backend.types.serializable.amount.Amount
import app.meetacy.backend.types.serializable.meetings.Meeting
import app.meetacy.di.global.di
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

sealed interface ListMeetingsResult {
    data class Success(val meetings: PagingResult<Meeting>) : ListMeetingsResult
    data object InvalidIdentity : ListMeetingsResult
}

interface ListMeetingsHistoryRepository {
    suspend fun getList(
        accessIdentity: AccessIdentity,
        amount: Amount,
        pagingId: PagingId?
    ): ListMeetingsResult
}

fun Route.listMeetingsHistory() = post("/list") {
    val listMeetingsHistoryRepository: ListMeetingsHistoryRepository by di.getting

    val params = call.receive<ListParam>()
    when (
        val result = listMeetingsHistoryRepository.getList(
            accessIdentity = params.token,
            amount = params.amount,
            pagingId = params.pagingId
        )
    ) {
        is ListMeetingsResult.Success -> call.respondSuccess(result.meetings)
        is ListMeetingsResult.InvalidIdentity -> call.respondFailure(Failure.InvalidToken)
    }
}
