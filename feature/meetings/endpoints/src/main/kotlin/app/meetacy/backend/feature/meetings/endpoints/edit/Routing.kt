package app.meetacy.backend.feature.meetings.endpoints.edit

import app.meetacy.backend.core.endpoints.accessIdentity
import app.meetacy.backend.endpoint.ktor.Failure
import app.meetacy.backend.endpoint.ktor.respondFailure
import app.meetacy.backend.endpoint.ktor.respondSuccess
import app.meetacy.backend.types.serializable.access.AccessIdentity
import app.meetacy.backend.types.serializable.datetime.Date
import app.meetacy.backend.types.serializable.file.FileIdentity
import app.meetacy.backend.types.serializable.location.Location
import app.meetacy.backend.types.serializable.meetings.Meeting
import app.meetacy.backend.types.serializable.meetings.MeetingIdentity
import app.meetacy.backend.types.serializable.optional.Optional
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable

@Serializable
data class EditMeetingParams(
    val meetingId: MeetingIdentity,
    val avatarId: Optional<FileIdentity?> = Optional.Undefined,
    val title: String?,
    val description: String?,
    val location: Location?,
    val date: Date?,
    val visibility: Meeting.Visibility?
)

sealed interface EditMeetingResult {
    class Success(val meeting: Meeting) : EditMeetingResult
    data object InvalidAccessIdentity : EditMeetingResult
    data object InvalidUtf8String : EditMeetingResult
    data object NullEditParameters : EditMeetingResult
    data object InvalidMeetingId : EditMeetingResult
    data object InvalidAvatarIdentity : EditMeetingResult
}

interface EditMeetingRepository {
    suspend fun editMeeting(
        token: AccessIdentity,
        meetingId: MeetingIdentity,
        avatarId: Optional<FileIdentity?>,
        title: String?,
        description: String?,
        location: Location?,
        date: Date?,
        visibility: Meeting.Visibility?
    ): EditMeetingResult
}

fun Route.editMeeting(repository: EditMeetingRepository) = put("/edit") {
    val params = call.receive<EditMeetingParams>()
    val token = call.accessIdentity()

    when (
        val result = repository.editMeeting(
            token,
            params.meetingId,
            params.avatarId,
            params.title,
            params.description,
            params.location,
            params.date,
            params.visibility
        )
    ) {
        is EditMeetingResult.Success -> call.respondSuccess(result.meeting)
        EditMeetingResult.InvalidAccessIdentity -> call.respondFailure(Failure.InvalidToken)
        EditMeetingResult.InvalidUtf8String -> call.respondFailure(Failure.InvalidUtf8String)
        EditMeetingResult.InvalidAvatarIdentity -> call.respondFailure(Failure.InvalidFileIdentity)
        EditMeetingResult.InvalidMeetingId -> call.respondFailure(Failure.InvalidMeetingIdentity)
        EditMeetingResult.NullEditParameters -> call.respondFailure(Failure.NullEditParams)
    }
}
