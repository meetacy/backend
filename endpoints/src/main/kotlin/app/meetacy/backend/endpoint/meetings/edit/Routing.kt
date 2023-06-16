package app.meetacy.backend.endpoint.meetings.edit

import app.meetacy.backend.endpoint.ktor.Failure
import app.meetacy.backend.endpoint.ktor.respondFailure
import app.meetacy.backend.endpoint.ktor.respondSuccess
import app.meetacy.backend.endpoint.types.meeting.Meeting
import app.meetacy.backend.types.serialization.OptionalSerializable
import app.meetacy.backend.types.serialization.access.AccessIdentitySerializable
import app.meetacy.backend.types.serialization.datetime.DateSerializable
import app.meetacy.backend.types.serialization.file.FileIdentitySerializable
import app.meetacy.backend.types.serialization.location.LocationSerializable
import app.meetacy.backend.types.serialization.meeting.MeetingIdentitySerializable
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable

@Serializable
data class EditMeetingParams(
    val token: AccessIdentitySerializable,
    val meetingId: MeetingIdentitySerializable,
    val avatarId: OptionalSerializable<FileIdentitySerializable?> = OptionalSerializable.Undefined,
    val title: String?,
    val description: String?,
    val location: LocationSerializable?,
    val date: DateSerializable?,
    val visibility: Meeting.Visibility?
)

sealed interface EditMeetingResult {
    class Success(val meeting: Meeting) : EditMeetingResult
    object InvalidAccessIdentity : EditMeetingResult
    object InvalidUtf8String : EditMeetingResult
    object NullEditParameters : EditMeetingResult
    object InvalidMeetingId : EditMeetingResult
    object InvalidAvatarIdentity : EditMeetingResult
}

interface EditMeetingRepository {
    suspend fun editMeeting(editMeetingParams: EditMeetingParams): EditMeetingResult
}

fun Route.editMeeting(editMeetingRepository: EditMeetingRepository) = post("/edit") {
    val params = call.receive<EditMeetingParams>()

    when (val result = editMeetingRepository.editMeeting(params)) {
        is EditMeetingResult.Success -> call.respondSuccess(result.meeting)
        EditMeetingResult.InvalidAccessIdentity -> call.respondFailure(Failure.InvalidToken)
        EditMeetingResult.InvalidUtf8String -> call.respondFailure(Failure.InvalidUtf8String)
        EditMeetingResult.InvalidAvatarIdentity -> call.respondFailure(Failure.InvalidFileIdentity)
        EditMeetingResult.InvalidMeetingId -> call.respondFailure(Failure.InvalidMeetingIdentity)
        EditMeetingResult.NullEditParameters -> call.respondFailure(Failure.NullEditParams)
    }
}
