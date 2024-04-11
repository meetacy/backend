package app.meetacy.backend.feature.meetings.endpoints.delete

import app.meetacy.backend.core.endpoints.accessIdentity
import app.meetacy.backend.core.endpoints.meetingId
import app.meetacy.backend.endpoint.ktor.Failure
import app.meetacy.backend.endpoint.ktor.respondFailure
import app.meetacy.backend.endpoint.ktor.respondSuccess
import app.meetacy.backend.types.serializable.access.AccessIdentity
import app.meetacy.backend.types.serializable.meetings.MeetingId
import io.ktor.server.application.*
import io.ktor.server.routing.*

sealed interface DeleteMeetingResult {
    data object Success : DeleteMeetingResult
    data object InvalidIdentity : DeleteMeetingResult
    data object MeetingNotFound : DeleteMeetingResult
}

interface DeleteMeetingRepository {
    suspend fun deleteMeeting(token: AccessIdentity, meetingId: MeetingId): DeleteMeetingResult
}

fun Route.deleteMeeting(repository: DeleteMeetingRepository) = delete("/delete") {
    val param = call.parameters.meetingId()
    val token = call.accessIdentity()
    when (repository.deleteMeeting(token, param)) {
        is DeleteMeetingResult.Success -> call.respondSuccess()
        is DeleteMeetingResult.InvalidIdentity -> call.respondFailure(Failure.InvalidToken)
        is DeleteMeetingResult.MeetingNotFound -> call.respondFailure(Failure.InvalidMeetingIdentity)
    }
}
