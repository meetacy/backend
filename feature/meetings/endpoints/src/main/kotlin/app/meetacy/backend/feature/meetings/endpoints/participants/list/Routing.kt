package app.meetacy.backend.feature.meetings.endpoints.participants.list

import app.meetacy.backend.endpoint.ktor.Failure
import app.meetacy.backend.endpoint.ktor.respondFailure
import app.meetacy.backend.endpoint.ktor.respondSuccess
import app.meetacy.backend.types.paging.serializable.PagingId
import app.meetacy.backend.types.paging.serializable.PagingResult
import app.meetacy.backend.types.serializable.amount.Amount
import app.meetacy.backend.types.serializable.meetings.MeetingIdentity
import app.meetacy.backend.types.serializable.users.User
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable
import app.meetacy.backend.types.serializable.access.AccessIdentity as AccessIdentitySerializable

@Serializable
data class ListMeetingParticipantsParams(
    val token: AccessIdentitySerializable,
    val meetingId: MeetingIdentity,
    val amount: Amount,
    val pagingId: PagingId? = null
)

fun interface ListMeetingParticipantsRepository {
    suspend fun listParticipants(params: ListMeetingParticipantsParams): ListParticipantsResult
}

sealed interface ListParticipantsResult {
    data object MeetingNotFound : ListParticipantsResult
    data object TokenInvalid : ListParticipantsResult
    class Success(val paging: PagingResult<User>) : ListParticipantsResult
}

fun Route.listMeetingParticipants(repository: ListMeetingParticipantsRepository) = post("/list") {
    val params = call.receive<ListMeetingParticipantsParams>()

    when (
        val result = repository.listParticipants(params)
    ) {
        is ListParticipantsResult.Success -> call.respondSuccess(result.paging)
        is ListParticipantsResult.TokenInvalid -> call.respondFailure(Failure.InvalidToken)
        is ListParticipantsResult.MeetingNotFound -> call.respondFailure(Failure.InvalidMeetingIdentity)
    }
}
