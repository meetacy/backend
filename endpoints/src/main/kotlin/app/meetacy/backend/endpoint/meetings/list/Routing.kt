package app.meetacy.backend.endpoint.meetings.list

import app.meetacy.backend.endpoint.types.Meeting
import app.meetacy.backend.types.AccessToken
import app.meetacy.backend.types.serialization.AccessTokenSerializable
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers

@Serializable
data class ListParam(
    val accessToken: AccessTokenSerializable
)

sealed interface ListMeetingsResult {
    class Success(val meetings: List<Meeting>) : ListMeetingsResult
    object TokenInvalid : ListMeetingsResult
}

interface MeetingsListRepository {
    suspend fun getList(accessToken: AccessToken): ListMeetingsResult
}

@Serializable
data class MeetingListResponse(
    val status: Boolean,
    val result: List<Meeting?>?,
    val errorCode: Int?,
    val errorMessage: String?
)


fun Route.listMeetings(meetingsListRepository: MeetingsListRepository) = post("/list") {
    val params = call.receive<ListParam>()
    when(val result = meetingsListRepository.getList(params.accessToken.type())) {
        is ListMeetingsResult.Success -> call.respond(
            MeetingListResponse(
                status = true,
                result = result.meetings,
                errorCode = null,
                errorMessage = null
            )
        )
        is ListMeetingsResult.TokenInvalid -> call.respond(
            MeetingListResponse(
                status = false,
                result = null,
                errorCode = 1,
                errorMessage = "Please provide a valid token" /* There is also an option
                  to make just one generic "List Meetings not found" response to
                  all three errors as a protection against brute force. */
            )
        )
    }
}
