package app.meetacy.backend.endpoint.meetings.participate


import app.meetacy.backend.endpoint.ktor.Failure
import app.meetacy.backend.endpoint.ktor.respondFailure
import app.meetacy.backend.endpoint.ktor.respondSuccess
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable
import app.meetacy.backend.types.serializable.access.AccessIdentity
import app.meetacy.backend.types.serializable.meeting.MeetingIdentity

@Serializable
data class ParticipateParam(
    val meetingId: MeetingIdentity,
    val token: AccessIdentity
)

sealed interface ParticipateMeetingResult {
    object Success : ParticipateMeetingResult
    object InvalidIdentity : ParticipateMeetingResult
    object MeetingNotFound : ParticipateMeetingResult
    object MeetingAlreadyParticipate : ParticipateMeetingResult
}

interface ParticipateMeetingRepository {
    suspend fun participateMeeting(
        meetingIdentity: app.meetacy.backend.types.meeting.MeetingIdentity,
        accessIdentity: AccessIdentity
    ): ParticipateMeetingResult
}

fun Route.participateMeeting(participateMeetingRepository: ParticipateMeetingRepository) = post("/participate") {
    val params = call.receive<ParticipateParam>()

    when (
        participateMeetingRepository.participateMeeting(
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
