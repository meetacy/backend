package app.meetacy.backend.endpoint.meetings.history.list

import app.meetacy.backend.endpoint.ktor.Failure
import app.meetacy.backend.endpoint.ktor.respondFailure
import app.meetacy.backend.endpoint.ktor.respondSuccess
import app.meetacy.backend.endpoint.types.Meeting
import app.meetacy.backend.types.access.AccessIdentity
import app.meetacy.backend.types.amount.Amount
import app.meetacy.backend.types.paging.PagingId
import app.meetacy.backend.types.paging.PagingResult
import app.meetacy.backend.types.serialization.access.AccessIdentitySerializable
import app.meetacy.backend.types.serialization.amount.AmountSerializable
import app.meetacy.backend.types.serialization.paging.PagingIdSerializable
import app.meetacy.backend.types.serialization.paging.serializable
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable

@Serializable
data class ListParam(
    val token: AccessIdentitySerializable,
    val amount: AmountSerializable,
    val pagingId: PagingIdSerializable? = null
)

sealed interface ListMeetingsResult {
    class Success(val meetings: PagingResult<List<Meeting>>) : ListMeetingsResult
    object InvalidIdentity : ListMeetingsResult
}

interface ListMeetingsHistoryRepository {
    suspend fun getList(params: ListParam): ListMeetingsResult
}

fun Route.listMeetingsHistory(listMeetingsHistoryRepository: ListMeetingsHistoryRepository) = post("/list") {
    val params = call.receive<ListParam>()
    when (
        val result = listMeetingsHistoryRepository.getList(params)
    ) {
        is ListMeetingsResult.Success -> call.respondSuccess(result.meetings.serializable())
        is ListMeetingsResult.InvalidIdentity -> call.respondFailure(Failure.InvalidAccessIdentity)
    }
}
