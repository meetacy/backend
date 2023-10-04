package app.meetacy.backend.feature.meetings.endpoints.participants.list

import app.meetacy.backend.core.endpoints.accessIdentity
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
import app.meetacy.backend.types.serializable.access.AccessIdentity

@Serializable
data class ListMeetingParticipantsParams(
    val meetingId: MeetingIdentity,
    val amount: Amount,
    val pagingId: PagingId? = null
)

fun interface ListMeetingParticipantsRepository {
    suspend fun listParticipants(
        token: AccessIdentity,
        meetingId: MeetingIdentity,
        amount: Amount,
        pagingId: PagingId?
    ): ListParticipantsResult
}

sealed interface ListParticipantsResult {
    data object MeetingNotFound : ListParticipantsResult
    data object TokenInvalid : ListParticipantsResult
    class Success(val paging: PagingResult<User>) : ListParticipantsResult
}

var count = 0

fun Route.listMeetingParticipants(repository: ListMeetingParticipantsRepository) = post("/list") {
    val params = call.receive<ListMeetingParticipantsParams>()
    val token = call.accessIdentity()

    count++
    println("request #$count")

    when (
        val result = repository.listParticipants(
            token,
            params.meetingId,
            params.amount,
            params.pagingId,
        )
    ) {
        is ListParticipantsResult.Success -> call.respondSuccess(result.paging)
        is ListParticipantsResult.TokenInvalid -> call.respondFailure(Failure.InvalidToken)
        is ListParticipantsResult.MeetingNotFound -> call.respondFailure(Failure.InvalidMeetingIdentity)
    }
}
