package app.meetacy.backend.endpoint.meetings.inviteCode.create

import app.meetacy.backend.endpoint.ktor.Failure
import app.meetacy.backend.endpoint.ktor.respondFailure
import app.meetacy.backend.endpoint.ktor.respondSuccess
import app.meetacy.backend.types.meeting.inviteCode.MeetingInviteCode
import app.meetacy.backend.types.serialization.access.AccessIdentitySerializable
import app.meetacy.backend.types.serialization.meeting.MeetingIdentitySerializable
import app.meetacy.backend.types.serialization.meeting.inviteCode.serializable
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable

@Serializable
data class CreateInviteCodeForMeetingParams(
    val token: AccessIdentitySerializable,
    val meetingIdentity: MeetingIdentitySerializable
)

interface CreateInviteCodeForMeetingRepository {
    suspend fun create(params: CreateInviteCodeForMeetingParams): CreateInviteCodeForMeetingResult
}

sealed interface CreateInviteCodeForMeetingResult {
    class Success(val meetingInviteCode: MeetingInviteCode) : CreateInviteCodeForMeetingResult
    object InvalidAccessIdentity : CreateInviteCodeForMeetingResult
}

fun Route.createInviteCode(repository: CreateInviteCodeForMeetingRepository) = post("/create") {
    val params = call.receive<CreateInviteCodeForMeetingParams>()

    when (val result = repository.create(params)) {
        is CreateInviteCodeForMeetingResult.Success -> call.respondSuccess(result.meetingInviteCode.serializable())
        CreateInviteCodeForMeetingResult.InvalidAccessIdentity -> call.respondFailure(Failure.InvalidAccessIdentity)
    }
}
