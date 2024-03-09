package app.meetacy.backend.feature.meetings.endpoints.quit

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
data class QuitMeetingParam(
    val meetingId: MeetingId
)

sealed interface QuitMeetingResult {
    data object Success : QuitMeetingResult
    data object InvalidIdentity : QuitMeetingResult
    data object MeetingNotFound : QuitMeetingResult
}

interface QuitMeetingRepository {
    suspend fun quitMeeting(token: AccessIdentity, meetingId: MeetingId): QuitMeetingResult
}

fun Route.quitMeeting(repository: QuitMeetingRepository) = post("/quit") {
    val param = call.receive<QuitMeetingParam>()
    val token = call.accessIdentity()
    when (repository.quitMeeting(token, param.meetingId)) {
        is QuitMeetingResult.Success -> call.respondSuccess()
        is QuitMeetingResult.InvalidIdentity -> call.respondFailure(Failure.InvalidToken)
        is QuitMeetingResult.MeetingNotFound -> call.respondFailure(Failure.InvalidMeetingIdentity)
    }
}
