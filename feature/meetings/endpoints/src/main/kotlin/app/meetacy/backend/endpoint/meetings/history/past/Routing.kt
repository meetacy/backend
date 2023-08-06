package app.meetacy.backend.endpoint.meetings.history.past

import app.meetacy.backend.endpoint.ktor.Failure
import app.meetacy.backend.endpoint.ktor.respondFailure
import app.meetacy.backend.endpoint.ktor.respondSuccess
import app.meetacy.backend.endpoint.meetings.history.list.ListMeetingsResult
import app.meetacy.backend.endpoint.meetings.history.list.ListParam
import app.meetacy.backend.types.access.AccessIdentity
import app.meetacy.backend.types.amount.Amount
import app.meetacy.backend.types.paging.PagingId
import app.meetacy.backend.types.serializable.access.type
import app.meetacy.backend.types.serializable.amount.type
import app.meetacy.backend.types.serialization.paging.serializable
import app.meetacy.di.global.di
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.routing.*

interface ListMeetingsPastRepository {
    suspend fun getList(
        accessIdentity: AccessIdentity,
        amount: Amount,
        pagingId: PagingId?
    ): ListMeetingsResult
}

@Suppress("DuplicatedCode")
fun Route.meetingsHistoryPast() = get("/past") {
    val repository: ListMeetingsPastRepository by di.getting

    val params = call.receive<ListParam>()

    when (val result = repository.getList(
            accessIdentity = params.token.type(),
            amount = params.amount.type(),
            pagingId = params.pagingId?.type()
    )) {
        is ListMeetingsResult.InvalidIdentity -> call.respondFailure(Failure.InvalidToken)
        is ListMeetingsResult.Success -> call.respondSuccess(result.meetings.serializable())
    }
}
