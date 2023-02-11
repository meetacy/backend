package app.meetacy.backend.endpoint.meetings.delete

import app.meetacy.backend.endpoint.ktor.respondFailure
import app.meetacy.backend.endpoint.ktor.respondSuccess
import app.meetacy.backend.types.serialization.AccessIdentitySerializable
import app.meetacy.backend.types.serialization.MeetingIdentitySerializable
import io.ktor.server.application.*
import io.ktor.server.request.*
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

fun Route.deleteMeeting(deleteMeetingRepository: DeleteMeetingRepository) = post("/delete") {
    val params = call.receive<DeleteMeetingParams>()

    when(deleteMeetingRepository.deleteMeeting(params)) {
        is DeleteMeetingResult.Success -> call.respondSuccess()
        is DeleteMeetingResult.InvalidIdentity -> call.respondFailure(
            1, "Please provide a valid identity"
        )
        is DeleteMeetingResult.MeetingNotFound -> call.respondFailure(
            2, "Please provide a valid meetingIdentity"
        )
    }
}
