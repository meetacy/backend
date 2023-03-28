package app.meetacy.backend.endpoint.meetings.history.list

import app.meetacy.backend.endpoint.ktor.Failure
import app.meetacy.backend.endpoint.ktor.respondFailure
import app.meetacy.backend.endpoint.ktor.respondSuccess
import app.meetacy.backend.endpoint.types.Meeting
import app.meetacy.backend.types.AccessIdentity
import app.meetacy.backend.types.Amount
import app.meetacy.backend.types.PagingId
import app.meetacy.backend.types.PagingResult
import app.meetacy.backend.types.serialization.AccessIdentitySerializable
import app.meetacy.backend.types.serialization.AmountSerializable
import app.meetacy.backend.types.serialization.PagingIdSerializable
import app.meetacy.backend.types.serialization.serializable
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable

@Serializable
data class ListParam(
    val accessIdentity: AccessIdentitySerializable,
    val amount: AmountSerializable,
    val pagingId: PagingIdSerializable? = null
)

sealed interface ListMeetingsResult {
    class Success(val meetings: PagingResult<List<Meeting>>) : ListMeetingsResult
    object InvalidIdentity : ListMeetingsResult
}

interface ListMeetingsHistoryRepository {
    suspend fun getList(
        accessIdentity: AccessIdentity,
        amount: Amount,
        pagingId: PagingId?
    ): ListMeetingsResult
}

fun Route.listMeetingsHistory(listMeetingsHistoryRepository: ListMeetingsHistoryRepository) = post("/list") {
    val params = call.receive<ListParam>()
    when (
        val result = listMeetingsHistoryRepository.getList(
            accessIdentity = params.accessIdentity.type(),
            amount = params.amount.type(),
            pagingId = params.pagingId?.type()
        )
    ) {
        is ListMeetingsResult.Success -> call.respondSuccess(result.meetings.serializable())
        is ListMeetingsResult.InvalidIdentity -> call.respondFailure(Failure.InvalidAccessIdentity)
    }
}
