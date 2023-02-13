package app.meetacy.backend.endpoint.meetings.participate


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
        is ParticipateMeetingResult.Success -> call.respondSuccess()
        is ParticipateMeetingResult.InvalidIdentity -> call.respondFailure(
            1, "Please provide a valid accessIdentity"
        )

        is ParticipateMeetingResult.MeetingNotFound -> call.respondFailure(
            2, "Please provide a valid id"
        )

        ParticipateMeetingResult.MeetingAlreadyParticipate -> call.respondFailure(
            3, "You are already participating in this meeting"
        )
    }
}
