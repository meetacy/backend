package app.meetacy.backend.endpoint.meetings.history.list

import app.meetacy.backend.endpoint.ktor.Failure
import app.meetacy.backend.endpoint.ktor.respondFailure
import app.meetacy.backend.endpoint.ktor.respondSuccess
import app.meetacy.backend.endpoint.types.meeting.Meeting
import app.meetacy.backend.types.access.AccessIdentity
import app.meetacy.backend.types.amount.Amount
import app.meetacy.backend.types.paging.PagingId
import app.meetacy.backend.types.paging.PagingResult
import app.meetacy.backend.types.serializable.access.type
import app.meetacy.backend.types.serializable.amount.type
import app.meetacy.backend.types.serialization.paging.PagingIdSerializable
import app.meetacy.backend.types.serialization.paging.serializable
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable
import app.meetacy.backend.types.serializable.access.AccessIdentity as AccessIdentitySerializable
import app.meetacy.backend.types.serializable.amount.Amount as AmountSerializable

@Serializable
data class ListParam(
    val token: AccessIdentitySerializable,
    val amount: AmountSerializable,
    val pagingId: PagingIdSerializable? = null
)

sealed interface ListMeetingsResult {
    class Success(val meetings: PagingResult<Meeting>) : ListMeetingsResult
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
            accessIdentity = params.token.type(),
            amount = params.amount.type(),
            pagingId = params.pagingId?.type()
        )
    ) {
        is ListMeetingsResult.Success -> call.respondSuccess(result.meetings.serializable())
        is ListMeetingsResult.InvalidIdentity -> call.respondFailure(Failure.InvalidToken)
    }
}
