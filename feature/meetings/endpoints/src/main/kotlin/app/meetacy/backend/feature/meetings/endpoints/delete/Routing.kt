package app.meetacy.backend.feature.meetings.endpoints.delete

import app.meetacy.backend.core.endpoints.accessIdentity
import app.meetacy.backend.endpoint.ktor.Failure
import app.meetacy.backend.endpoint.ktor.respondFailure
import app.meetacy.backend.endpoint.ktor.respondSuccess
import app.meetacy.backend.types.serializable.access.AccessIdentity
import app.meetacy.backend.types.serializable.meetings.MeetingId
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable

@Serializable
data class DeleteMeetingParam(
    val meetingId: MeetingId
)

sealed interface DeleteMeetingResult {
    data object Success : DeleteMeetingResult
    data object InvalidIdentity : DeleteMeetingResult
    data object MeetingNotFound : DeleteMeetingResult
}

interface DeleteMeetingRepository {
    suspend fun deleteMeeting(token: AccessIdentity, meetingId: MeetingId): DeleteMeetingResult
}

fun Route.deleteMeeting(repository: DeleteMeetingRepository) = delete("/delete") {
    val param = call.receive<DeleteMeetingParam>()
    val token = call.accessIdentity()
    when (repository.deleteMeeting(token, param.meetingId)) {
        is DeleteMeetingResult.Success -> call.respondSuccess()
        is DeleteMeetingResult.InvalidIdentity -> call.respondFailure(Failure.InvalidToken)
        is DeleteMeetingResult.MeetingNotFound -> call.respondFailure(Failure.InvalidMeetingIdentity)
    }
}
