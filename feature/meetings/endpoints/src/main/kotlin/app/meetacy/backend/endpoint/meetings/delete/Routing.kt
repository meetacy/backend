package app.meetacy.backend.endpoint.meetings.delete

import app.meetacy.backend.endpoint.ktor.Failure
import app.meetacy.backend.endpoint.ktor.respondFailure
import app.meetacy.backend.endpoint.ktor.respondSuccess
import app.meetacy.backend.types.serializable.access.AccessIdentity
import app.meetacy.backend.types.serialization.meeting.MeetingIdentitySerializable
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable

@Serializable
data class DeleteMeetingParams(
    val token: AccessIdentity,
    val meetingId: MeetingIdentitySerializable
)

sealed interface DeleteMeetingResult {
    data object Success : DeleteMeetingResult
    data object InvalidIdentity : DeleteMeetingResult
    data object MeetingNotFound : DeleteMeetingResult
}

interface DeleteMeetingRepository {
    suspend fun deleteMeeting(deleteMeetingParams: DeleteMeetingParams): DeleteMeetingResult
}

fun Route.deleteMeeting(deleteMeetingRepository: DeleteMeetingRepository) = post("/delete") {
    val params = call.receive<DeleteMeetingParams>()

    when (deleteMeetingRepository.deleteMeeting(params)) {

        is DeleteMeetingResult.Success -> call.respondSuccess()

        is DeleteMeetingResult.InvalidIdentity -> call.respondFailure(Failure.InvalidToken)

        is DeleteMeetingResult.MeetingNotFound -> call.respondFailure(Failure.InvalidMeetingIdentity)
    }
}
