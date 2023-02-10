package app.meetacy.backend.endpoint.meetings.avatar.delete

import app.meetacy.backend.endpoint.ktor.respondEmptySuccess
import app.meetacy.backend.endpoint.ktor.respondFailure
import app.meetacy.backend.types.serialization.AccessIdentitySerializable
import app.meetacy.backend.types.serialization.MeetingIdentitySerializable
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
    val meetingIdentity: MeetingIdentitySerializable,
    val accessIdentity: AccessIdentitySerializable
)

interface DeleteMeetingAvatarRepository {
    suspend fun deleteAvatar(deleteMeetingAvatarParams: DeleteMeetingAvatarParams): DeleteMeetingAvatarResult
}

fun Route.deleteAvatar(provider: DeleteMeetingAvatarRepository) = post("/delete") {
    val params = call.receive<DeleteMeetingAvatarParams>()

    when(provider.deleteAvatar(params)) {
        is DeleteMeetingAvatarResult.Success -> call.respondEmptySuccess()
        DeleteMeetingAvatarResult.MeetingNotFound -> call.respondFailure(
            1, "Please provide a valid meetingIdentity"
        )
        DeleteMeetingAvatarResult.InvalidAccessIdentity -> call.respondFailure(
            1, "Please provide a valid identity"
        )
    }
}
