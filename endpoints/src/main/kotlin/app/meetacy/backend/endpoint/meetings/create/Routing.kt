package app.meetacy.backend.endpoint.meetings.create

import app.meetacy.backend.endpoint.auth.generate.GenerateTokenResponse
import app.meetacy.backend.endpoint.types.Meeting
import app.meetacy.backend.types.serialization.AccessIdentitySerializable
import app.meetacy.backend.types.serialization.DateSerializable
import app.meetacy.backend.types.serialization.LocationSerializable
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable

@Serializable
data class CreateParam(
    val accessIdentity: AccessIdentitySerializable,
    val title: String?,
    val description: String?,
    val date: DateSerializable,
    val location: LocationSerializable
)

sealed interface CreateMeetingResult {
    class Success(val meeting: Meeting) : CreateMeetingResult
    object TokenInvalid : CreateMeetingResult
    object InvalidUtf8String : CreateMeetingResult
}

interface CreateMeetingRepository {
    suspend fun createMeeting(createParam: CreateParam) : CreateMeetingResult
}

@Serializable
data class CreateMeetResponse(
    val status: Boolean,
    val errorCode: Int?,
    val errorMessage: String?,
    val result: Meeting?

)

fun Route.createMeeting(createMeetingRepository: CreateMeetingRepository) = post("/create") {
    val params = call.receive<CreateParam>()

    when(val result = createMeetingRepository.createMeeting(params)) {
        is CreateMeetingResult.Success -> call.respond(
            CreateMeetResponse(
                status = true,
                errorCode = null,
                errorMessage = null,
                result = result.meeting
            )
        )
        CreateMeetingResult.TokenInvalid -> call.respond(
            CreateMeetResponse(
                status = false,
                errorCode = 1,
                errorMessage = "Please provide a valid identity" /* There is also an option
                  to make just one generic "Meeting not create" response to
                  all three errors as a protection against brute force. */,
                result = null
            )
        )
        CreateMeetingResult.InvalidUtf8String -> call.respond(
            GenerateTokenResponse(
                status = false,
                errorCode = 2,
                errorMessage = "Please provide a valid title or description",
                result = null
            )
        )
    }
}
