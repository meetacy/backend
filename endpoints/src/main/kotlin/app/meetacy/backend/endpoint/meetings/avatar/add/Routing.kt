package app.meetacy.backend.endpoint.meetings.avatar.add

import app.meetacy.backend.endpoint.users.avatar.add.AddUserAvatarResponse
import app.meetacy.backend.types.serialization.AccessIdentitySerializable
import app.meetacy.backend.types.serialization.FileIdentitySerializable
import app.meetacy.backend.types.serialization.MeetingIdentitySerializable
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable

sealed interface AddMeetingAvatarResult {
    object Success : AddMeetingAvatarResult
    object MeetingNotFound : AddMeetingAvatarResult
    object InvalidMeetingAvatarIdentity : AddMeetingAvatarResult
    object InvalidAccessIdentity : AddMeetingAvatarResult
}

@Serializable
data class AddMeetingAvatarParams(
    val meetingIdentity: MeetingIdentitySerializable,
    val accessIdentity: AccessIdentitySerializable,
    val avatarIdentity: FileIdentitySerializable
)

@Serializable
data class AddAvatarResponse(
    val status: Boolean = false,
    val errorCode: Int? = null,
    val errorMessage: String? = null
)

interface AddMeetingAvatarRepository {
    suspend fun addAvatar(addMeetingAvatarParams: AddMeetingAvatarParams): AddMeetingAvatarResult
}

fun Route.addAvatar(provider: AddMeetingAvatarRepository) = post("/add") {
    val params = call.receive<AddMeetingAvatarParams>()

    when(provider.addAvatar(params)) {
        is AddMeetingAvatarResult.Success -> call.respond(
            AddAvatarResponse(
                status = true,
                errorCode = null,
                errorMessage = null
            )
        )
        AddMeetingAvatarResult.MeetingNotFound -> call.respond(
            AddAvatarResponse(
                status = false,
                errorCode = 1,
                errorMessage = "Please provide a valid meetingIdentity"
            )
        )
        AddMeetingAvatarResult.InvalidMeetingAvatarIdentity -> call.respond(
            AddAvatarResponse(
                status = false,
                errorCode = 2,
                errorMessage = "Please provide a valid fileIdentity"
            )
        )
        AddMeetingAvatarResult.InvalidAccessIdentity -> call.respond(
            AddUserAvatarResponse(
                status = false,
                errorCode = 1,
                errorMessage = "Please provide a valid identity"
            )
        )
    }
}