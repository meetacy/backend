package app.meetacy.backend.endpoint.meetings.inviteCode.getMeeting

import app.meetacy.backend.endpoint.ktor.Failure
import app.meetacy.backend.endpoint.ktor.respondFailure
import app.meetacy.backend.endpoint.ktor.respondSuccess
import app.meetacy.backend.endpoint.types.Meeting
import app.meetacy.backend.types.serialization.access.AccessIdentitySerializable
import app.meetacy.backend.types.serialization.meeting.inviteCode.MeetingInviteCodeSerializable
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable

@Serializable
data class GetMeetingByInviteCodeParams(
    val token: AccessIdentitySerializable,
    val inviteCode: MeetingInviteCodeSerializable
)

interface GetMeetingByInviteCodeRepository {
    suspend fun create(params: GetMeetingByInviteCodeParams): GetMeetingByInviteCodeResult
}

sealed interface GetMeetingByInviteCodeResult {
    class Success(val meeting: Meeting) : GetMeetingByInviteCodeResult
    object InvalidAccessIdentity : GetMeetingByInviteCodeResult
    object InvalidInviteCode : GetMeetingByInviteCodeResult
}

fun Route.getMeetingByInviteCode(repository: GetMeetingByInviteCodeRepository) = post("/getMeeting") {
    val params = call.receive<GetMeetingByInviteCodeParams>()

    when (val result = repository.create(params)) {
        is GetMeetingByInviteCodeResult.Success -> call.respondSuccess(result.meeting)
        GetMeetingByInviteCodeResult.InvalidAccessIdentity -> call.respondFailure(Failure.InvalidAccessIdentity)
        GetMeetingByInviteCodeResult.InvalidInviteCode -> call.respondFailure(Failure.InvalidInviteCode)
    }
}
