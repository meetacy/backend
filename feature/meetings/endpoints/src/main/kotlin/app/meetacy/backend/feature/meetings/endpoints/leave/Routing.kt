package app.meetacy.backend.feature.meetings.endpoints.leave

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
data class LeaveMeetingParam(
    val meetingId: MeetingId
)

sealed interface LeaveMeetingResult {
    data object Success : LeaveMeetingResult
    data object InvalidIdentity : LeaveMeetingResult
    data object MeetingNotFound : LeaveMeetingResult
}

interface LeaveMeetingRepository {
    suspend fun leaveMeeting(token: AccessIdentity, meetingId: MeetingId): LeaveMeetingResult
}

fun Route.leaveMeeting(repository: LeaveMeetingRepository) = post("/leave") {
    val param = call.receive<LeaveMeetingParam>()
    val token = call.accessIdentity()
    when (repository.leaveMeeting(token, param.meetingId)) {
        is LeaveMeetingResult.Success -> call.respondSuccess()
        is LeaveMeetingResult.InvalidIdentity -> call.respondFailure(Failure.InvalidToken)
        is LeaveMeetingResult.MeetingNotFound -> call.respondFailure(Failure.InvalidMeetingIdentity)
    }
}
