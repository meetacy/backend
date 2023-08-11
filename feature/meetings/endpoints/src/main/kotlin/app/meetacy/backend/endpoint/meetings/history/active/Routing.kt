package app.meetacy.backend.endpoint.meetings.history.active

import app.meetacy.backend.endpoint.ktor.Failure
import app.meetacy.backend.endpoint.ktor.respondFailure
import app.meetacy.backend.endpoint.ktor.respondSuccess
import app.meetacy.backend.endpoint.meetings.history.list.ListMeetingsResult
import app.meetacy.backend.endpoint.meetings.history.list.ListParam
import app.meetacy.backend.types.paging.serializable.PagingId
import app.meetacy.backend.types.serializable.access.AccessIdentity
import app.meetacy.backend.types.serializable.amount.Amount
import app.meetacy.di.global.di
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.routing.*

interface ListMeetingsActiveRepository {
    suspend fun getList(
        accessIdentity: AccessIdentity,
        amount: Amount,
        pagingId: PagingId?
    ): ListMeetingsResult
}

fun Route.meetingsHistoryActive() = get("/active") {
    val repository: ListMeetingsActiveRepository by di.getting

    val params = call.receive<ListParam>()

    when (
        val result = repository.getList(
            accessIdentity = params.token,
            amount = params.amount,
            pagingId = params.pagingId
        )
    ) {
        is ListMeetingsResult.InvalidIdentity -> call.respondFailure(Failure.InvalidToken)
        is ListMeetingsResult.Success -> call.respondSuccess(result.meetings)
    }
}
