package app.meetacy.backend.feature.meetings.endpoints.history.past

import app.meetacy.backend.endpoint.ktor.Failure
import app.meetacy.backend.endpoint.ktor.respondFailure
import app.meetacy.backend.endpoint.ktor.respondSuccess
import app.meetacy.backend.feature.meetings.endpoints.history.list.ListMeetingsResult
import app.meetacy.backend.feature.meetings.endpoints.history.list.ListParam
import app.meetacy.backend.types.paging.serializable.PagingId
import app.meetacy.backend.types.serializable.access.AccessIdentity
import app.meetacy.backend.types.serializable.amount.Amount
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
fun Route.meetingsHistoryPast(provider: ListMeetingsPastRepository) = get("/past") {
    val params = call.receive<ListParam>()

    when (val result = provider.getList(
            accessIdentity = params.token,
            amount = params.amount,
            pagingId = params.pagingId
    )) {
        is ListMeetingsResult.InvalidIdentity -> call.respondFailure(Failure.InvalidToken)
        is ListMeetingsResult.Success -> call.respondSuccess(result.meetings)
    }
}
