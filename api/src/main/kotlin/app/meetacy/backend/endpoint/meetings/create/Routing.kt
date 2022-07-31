@file:UseSerializers(LocationSerializer::class, AccessTokenSerializer::class, DateSerializer::class)

package app.meetacy.backend.endpoint.meetings.create

import app.meetacy.backend.domain.AccessToken
import app.meetacy.backend.domain.Date
import app.meetacy.backend.domain.Location
import app.meetacy.backend.endpoint.types.Meeting
import app.meetacy.backend.serialization.AccessTokenSerializer
import app.meetacy.backend.serialization.DateSerializer
import app.meetacy.backend.serialization.LocationSerializer
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers

@Serializable
data class CreateParam(
    val accessToken: AccessToken,
    val title: String?,
    val description: String?,
    val date: Date,
    val location: Location
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
    val errorMessage: String?
)

fun Route.createMeeting(createMeetingRepository: CreateMeetingRepository) = post("/create") {
    val params = call.receive<CreateParam>()

    when(createMeetingRepository.createMeeting(params)) {
        is CreateMeetingResult.Success -> call.respond(
            CreateMeetResponse(
                status = true,
                errorCode = null,
                errorMessage = null
            )
        )
        is CreateMeetingResult.TokenInvalid -> call.respond(
            CreateMeetResponse(
                status = false,
                errorCode = 1,
                errorMessage = "Please provide a valid token" /* There is also an option
                  to make just one generic "Meeting not create" response to
                  all three errors as a protection against brute force. */
            )
        )
    }
}
