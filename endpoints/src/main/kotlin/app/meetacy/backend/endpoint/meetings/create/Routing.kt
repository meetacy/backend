package app.meetacy.backend.endpoint.meetings.create

import app.meetacy.backend.endpoint.types.Meeting
import app.meetacy.backend.types.serialization.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable

@Serializable
data class CreateParam(
    val accessToken: AccessTokenSerializable,
    val title: String?,
    val description: String?,
    val date: DateSerializable,
    val location: LocationSerializable
)

sealed interface CreateMeetingResult {
    class Success(val meeting: Meeting) : CreateMeetingResult
    object TokenInvalid : CreateMeetingResult
}

interface CreateMeetingRepository {
    suspend fun createMeeting(createParam: CreateParam) : CreateMeetingResult
}

@Serializable
data class CreateMeetResponse(
    val status: Boolean,
    val errorCode: Int?,
    val errorMessage: String?,
    val resultId: MeetingIdSerializable?,
    val resultHash: AccessHashSerializable?
)

fun Route.createMeeting(createMeetingRepository: CreateMeetingRepository) = post("/create") {
    val params = call.receive<CreateParam>()

    when(val result = createMeetingRepository.createMeeting(params)) {
        is CreateMeetingResult.Success -> call.respond(
            CreateMeetResponse(
                status = true,
                errorCode = null,
                errorMessage = null,
                resultId = result.meeting.id,
                resultHash = result.meeting.accessHash
            )
        )
        is CreateMeetingResult.TokenInvalid -> call.respond(
            CreateMeetResponse(
                status = false,
                errorCode = 1,
                errorMessage = "Please provide a valid token" /* There is also an option
                  to make just one generic "Meeting not create" response to
                  all three errors as a protection against brute force. */,
                resultId = null,
                resultHash = null
            )
        )
    }
}
