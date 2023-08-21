package app.meetacy.backend.feature.meetings.endpoints.participate


import app.meetacy.backend.endpoint.ktor.Failure
import app.meetacy.backend.endpoint.ktor.respondFailure
import app.meetacy.backend.endpoint.ktor.respondSuccess
import app.meetacy.backend.types.serializable.access.AccessIdentity
import app.meetacy.backend.types.serializable.meetings.MeetingIdentity
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable

@Serializable
data class ParticipateParam(
    val meetingId: MeetingIdentity,
    val token: AccessIdentity
)

sealed interface ParticipateMeetingResult {
    data object Success : ParticipateMeetingResult
    data object InvalidIdentity : ParticipateMeetingResult
    data object MeetingNotFound : ParticipateMeetingResult
    data object MeetingAlreadyParticipate : ParticipateMeetingResult
}

interface ParticipateMeetingRepository {
    suspend fun participateMeeting(
        meetingIdentity: MeetingIdentity,
        accessIdentity: AccessIdentity
    ): ParticipateMeetingResult
}

fun Route.participateMeeting(provider: ParticipateMeetingRepository) = post("/participate") {
    val params = call.receive<ParticipateParam>()

    when (
        provider.participateMeeting(
            params.meetingId,
            params.token
        )
    ) {
        ParticipateMeetingResult.Success -> call.respondSuccess()
        ParticipateMeetingResult.InvalidIdentity -> call.respondFailure(Failure.InvalidToken)
        ParticipateMeetingResult.MeetingNotFound -> call.respondFailure(Failure.InvalidMeetingIdentity)
        ParticipateMeetingResult.MeetingAlreadyParticipate -> call.respondFailure(Failure.MeetingAlreadyParticipate)
    }
}
