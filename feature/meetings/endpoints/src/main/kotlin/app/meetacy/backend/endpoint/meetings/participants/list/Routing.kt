package app.meetacy.backend.endpoint.meetings.participants.list

import app.meetacy.backend.endpoint.ktor.Failure
import app.meetacy.backend.endpoint.ktor.respondFailure
import app.meetacy.backend.endpoint.ktor.respondSuccess
import app.meetacy.backend.endpoint.types.user.User
import app.meetacy.backend.types.serializable.access.AccessIdentity as AccessIdentitySerializable
import app.meetacy.backend.types.serializable.amount.Amount
import app.meetacy.backend.types.serialization.meeting.MeetingIdentitySerializable
import app.meetacy.backend.types.serialization.paging.PagingIdSerializable
import app.meetacy.backend.types.serialization.paging.PagingResultSerializable
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable

@Serializable
data class ListMeetingParticipantsParams(
    val token: AccessIdentitySerializable,
    val meetingId: MeetingIdentitySerializable,
    val amount: Amount,
    val pagingId: PagingIdSerializable? = null
)

interface ListMeetingParticipantsRepository {
    suspend fun listParticipants(params: ListMeetingParticipantsParams): ListParticipantsResult
}

sealed interface ListParticipantsResult {
    object MeetingNotFound : ListParticipantsResult
    object TokenInvalid : ListParticipantsResult
    class Success(val paging: PagingResultSerializable<User>) : ListParticipantsResult
}

fun Route.listMeetingParticipants(
    repository: ListMeetingParticipantsRepository
) = post("/list") {
    val params = call.receive<ListMeetingParticipantsParams>()

    when (
        val result = repository.listParticipants(params)
    ) {
        is ListParticipantsResult.Success -> call.respondSuccess(result.paging)
        is ListParticipantsResult.TokenInvalid -> call.respondFailure(Failure.InvalidToken)
        is ListParticipantsResult.MeetingNotFound -> call.respondFailure(Failure.InvalidMeetingIdentity)
    }
}
