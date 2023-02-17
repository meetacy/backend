package app.meetacy.backend.endpoint.meetings.avatar.add

import app.meetacy.backend.endpoint.ktor.respondFailure
import app.meetacy.backend.endpoint.ktor.respondSuccess
import app.meetacy.backend.types.serialization.AccessIdentitySerializable
import app.meetacy.backend.types.serialization.FileIdentitySerializable
import app.meetacy.backend.types.serialization.MeetingIdentitySerializable
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable

sealed interface AddMeetingAvatarResult {
    object Success : AddMeetingAvatarResult
    object MeetingNotFound : AddMeetingAvatarResult
    object InvalidMeetingFileIdentity : AddMeetingAvatarResult
    object InvalidAccessIdentity : AddMeetingAvatarResult
}

@Serializable
data class AddMeetingAvatarParams(
    val meetingIdentity: MeetingIdentitySerializable,
    val accessIdentity: AccessIdentitySerializable,
    val fileIdentity: FileIdentitySerializable
)

interface AddMeetingAvatarRepository {
    suspend fun addAvatar(addMeetingAvatarParams: AddMeetingAvatarParams): AddMeetingAvatarResult
}

fun Route.addAvatar(provider: AddMeetingAvatarRepository) = post("/add") {
    val params = call.receive<AddMeetingAvatarParams>()

    when (provider.addAvatar(params)) {
        is AddMeetingAvatarResult.Success -> call.respondSuccess()
        AddMeetingAvatarResult.MeetingNotFound -> call.respondFailure(
            1, "Please provide a valid meetingIdentity"
        )

        AddMeetingAvatarResult.InvalidMeetingFileIdentity -> call.respondFailure(
            2, "Please provide a valid fileIdentity"
        )

        AddMeetingAvatarResult.InvalidAccessIdentity -> call.respondFailure(
            3, "Please provide a valid accessIdentity"
        )
    }
}
