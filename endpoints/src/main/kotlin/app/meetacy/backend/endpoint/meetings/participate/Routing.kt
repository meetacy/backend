package app.meetacy.backend.endpoint.meetings.participate


import app.meetacy.backend.endpoint.ktor.Failure
import app.meetacy.backend.endpoint.ktor.respondFailure
import app.meetacy.backend.endpoint.ktor.respondSuccess
import app.meetacy.backend.types.AccessIdentity
import app.meetacy.backend.types.MeetingIdentity
import app.meetacy.backend.types.serialization.AccessIdentitySerializable
import app.meetacy.backend.types.serialization.MeetingIdentitySerializable
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable

@Serializable
data class ParticipateParam(
    val meetingIdentity: MeetingIdentitySerializable,
    val accessIdentity: AccessIdentitySerializable
)

sealed interface ParticipateMeetingResult {
    object Success : ParticipateMeetingResult
    object InvalidIdentity : ParticipateMeetingResult
    object MeetingNotFound : ParticipateMeetingResult
    object MeetingAlreadyParticipate : ParticipateMeetingResult
    object FinishedMeeting : ParticipateMeetingResult
}

interface ParticipateMeetingRepository {
    suspend fun participateMeeting(
        meetingIdentity: MeetingIdentity,
        accessIdentity: AccessIdentity
    ): ParticipateMeetingResult
}

fun Route.participateMeeting(participateMeetingRepository: ParticipateMeetingRepository) = post("/participate") {
    val params = call.receive<ParticipateParam>()

    when (
        participateMeetingRepository.participateMeeting(
            params.meetingIdentity.type(),
            params.accessIdentity.type()
        )
    ) {

        ParticipateMeetingResult.Success -> call.respondSuccess()

        ParticipateMeetingResult.InvalidIdentity -> call.respondFailure(Failure.InvalidAccessIdentity)
        ParticipateMeetingResult.MeetingNotFound -> call.respondFailure(Failure.InvalidMeetingIdentity)
        ParticipateMeetingResult.MeetingAlreadyParticipate -> call.respondFailure(Failure.MeetingAlreadyParticipate)
        ParticipateMeetingResult.FinishedMeeting -> call.respondFailure(Failure.FinishedMeeting)
    }
}
