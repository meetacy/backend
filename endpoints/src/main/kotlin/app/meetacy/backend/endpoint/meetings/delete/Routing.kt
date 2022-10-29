package app.meetacy.backend.endpoint.meetings.delete

import app.meetacy.backend.types.serialization.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable

@Serializable
data class DeleteMeetingParams(
    val accessIdentity: AccessIdentitySerializable,
    val meetingIdentity: MeetingIdentitySerializable
)

sealed interface DeleteMeetingResult {
    object Success : DeleteMeetingResult
    object InvalidIdentity : DeleteMeetingResult
    object MeetingNotFound : DeleteMeetingResult
}

interface DeleteMeetingRepository {
    suspend fun deleteMeeting(deleteMeetingParams: DeleteMeetingParams): DeleteMeetingResult
}

@Serializable
data class DeleteMeetResponse(
    val status: Boolean,
    val errorCode: Int?,
    val errorMessage: String?

)

fun Route.deleteMeeting(deleteMeetingRepository: DeleteMeetingRepository) = post("/delete") {
    val params = call.receive<DeleteMeetingParams>()

    when(deleteMeetingRepository.deleteMeeting(params)) {
        is DeleteMeetingResult.Success -> call.respond(
            DeleteMeetResponse(
                status = true,
                errorCode = null,
                errorMessage = null
            )
        )
        is DeleteMeetingResult.InvalidIdentity -> call.respond(
            DeleteMeetResponse(
                status = false,
                errorCode = 1,
                errorMessage = "Please provide a valid identity"
            )
        )
        is DeleteMeetingResult.MeetingNotFound -> call.respond(
            DeleteMeetResponse(
                status = false,
                errorCode = 2,
                errorMessage = "Please provide a valid meetingIdentity"
            )
        )
    }
}
