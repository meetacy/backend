package app.meetacy.backend.endpoint.meetings.get

import app.meetacy.backend.endpoint.ktor.respondFailure
import app.meetacy.backend.endpoint.ktor.respondSuccess
import app.meetacy.backend.endpoint.types.Meeting
import app.meetacy.backend.types.AccessIdentity
import app.meetacy.backend.types.MeetingIdentity
import app.meetacy.backend.types.serialization.AccessIdentitySerializable
import app.meetacy.backend.types.serialization.MeetingIdentitySerializable
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable

@Serializable
data class GetMeetingsParam(
    val accessIdentity: AccessIdentitySerializable,
    val meetingIdentity: MeetingIdentitySerializable
)

sealed interface GetMeetingResult {
    class Success(val meeting: Meeting) : GetMeetingResult
    object InvalidAccessIdentity : GetMeetingResult
    object MeetingNotFound : GetMeetingResult
}

interface GetMeetingRepository {
    suspend fun getMeeting(
        accessIdentity: AccessIdentity,
        meetingIdentity: MeetingIdentity
    ): GetMeetingResult
}

fun Route.getMeetings(getMeetingRepository: GetMeetingRepository) = post("/get") {
    val params = call.receive<GetMeetingsParam>()

    when (
        val result = getMeetingRepository.getMeeting(
            params.accessIdentity.type(),
            params.meetingIdentity.type()
        )
    ) {
        is GetMeetingResult.Success -> call.respondSuccess(
            result.meeting
        )

        is GetMeetingResult.InvalidAccessIdentity -> call.respondFailure(
            1, "Please provide a valid accessIdentity"
        )

        is GetMeetingResult.MeetingNotFound -> call.respondFailure(
            2, "Please provide a valid meetingIdentity"
        )
    }
}
