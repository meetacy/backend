package app.meetacy.backend.endpoint.meetings.avatar.delete

import app.meetacy.backend.types.serialization.AccessIdentitySerializable
import app.meetacy.backend.types.serialization.FileIdentitySerializable
import app.meetacy.backend.types.serialization.MeetingIdentitySerializable
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable

sealed interface DeleteMeetingAvatarResult {
    object Success : DeleteMeetingAvatarResult
    object MeetingNotFound : DeleteMeetingAvatarResult
    object InvalidMeetingAvatarIdentity : DeleteMeetingAvatarResult
    object InvalidAccessIdentity : DeleteMeetingAvatarResult
}

@Serializable
data class DeleteMeetingAvatarParams(
    val meetingIdentity: MeetingIdentitySerializable,
    val accessIdentity: AccessIdentitySerializable,
    val avatarIdentity: FileIdentitySerializable
)

@Serializable
data class DeleteMeetingAvatarResponse(
    val status: Boolean = false,
    val errorCode: Int? = null,
    val errorMessage: String? = null
)

interface DeleteMeetingAvatarRepository {
    suspend fun deleteAvatar(deleteMeetingAvatarParams: DeleteMeetingAvatarParams): DeleteMeetingAvatarResult
}

fun Route.deleteAvatar(provider: DeleteMeetingAvatarRepository) = post("/delete") {
    val params = call.receive<DeleteMeetingAvatarParams>()

    when(provider.deleteAvatar(params)) {
        is DeleteMeetingAvatarResult.Success -> call.respond(
            DeleteMeetingAvatarResponse(
                status = true,
                errorCode = null,
                errorMessage = null
            )
        )
        DeleteMeetingAvatarResult.MeetingNotFound -> call.respond(
            DeleteMeetingAvatarResponse(
                status = false,
                errorCode = 1,
                errorMessage = "Please provide a valid meetingIdentity"
            )
        )
        DeleteMeetingAvatarResult.InvalidMeetingAvatarIdentity -> call.respond(
            DeleteMeetingAvatarResponse(
                status = false,
                errorCode = 2,
                errorMessage = "Please provide a valid fileIdentity"
            )
        )
        DeleteMeetingAvatarResult.InvalidAccessIdentity -> call.respond(
            DeleteMeetingAvatarResponse(
                status = false,
                errorCode = 1,
                errorMessage = "Please provide a valid identity"
            )
        )
    }
}
