package app.meetacy.backend.endpoint.meetings.avatar.delete

import app.meetacy.backend.endpoint.ktor.Failure
import app.meetacy.backend.endpoint.ktor.respondFailure
import app.meetacy.backend.endpoint.ktor.respondSuccess
import app.meetacy.backend.types.serialization.access.AccessIdentitySerializable
import app.meetacy.backend.types.serialization.meeting.MeetingIdentitySerializable
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable

sealed interface DeleteMeetingAvatarResult {
    object Success : DeleteMeetingAvatarResult
    object MeetingNotFound : DeleteMeetingAvatarResult
    object InvalidAccessIdentity : DeleteMeetingAvatarResult
}

@Serializable
data class DeleteMeetingAvatarParams(
    val meetingId: MeetingIdentitySerializable,
    val token: AccessIdentitySerializable
)

interface DeleteMeetingAvatarRepository {
    suspend fun deleteAvatar(deleteMeetingAvatarParams: DeleteMeetingAvatarParams): DeleteMeetingAvatarResult
}

fun Route.deleteAvatar(provider: DeleteMeetingAvatarRepository) = post("/delete") {
    val params = call.receive<DeleteMeetingAvatarParams>()

    when (provider.deleteAvatar(params)) {

        is DeleteMeetingAvatarResult.Success -> call.respondSuccess()

        DeleteMeetingAvatarResult.MeetingNotFound -> call.respondFailure(Failure.InvalidMeetingIdentity)
        DeleteMeetingAvatarResult.InvalidAccessIdentity -> call.respondFailure(Failure.InvalidAccessIdentity)
    }
}
