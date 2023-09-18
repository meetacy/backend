
package app.meetacy.backend.feature.meetings.endpoints.create

import app.meetacy.backend.endpoint.ktor.Failure
import app.meetacy.backend.endpoint.ktor.respondFailure
import app.meetacy.backend.endpoint.ktor.respondSuccess
import app.meetacy.backend.types.serializable.access.AccessIdentity
import app.meetacy.backend.types.serializable.datetime.Date
import app.meetacy.backend.types.serializable.file.FileIdentity
import app.meetacy.backend.types.serializable.location.Location
import app.meetacy.backend.types.serializable.meetings.Meeting
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable

@Serializable
data class CreateParam(
    val token: AccessIdentity,
    val title: String?,
    val description: String?,
    val date: Date,
    val location: Location,
    val visibility: Meeting.Visibility,
    val avatarId: FileIdentity?
)

sealed interface CreateMeetingResult {
    data class Success(val meeting: Meeting) : CreateMeetingResult
    data object InvalidAccessIdentity : CreateMeetingResult
    data object InvalidUtf8String : CreateMeetingResult
    data object InvalidFileIdentity : CreateMeetingResult
}

interface CreateMeetingRepository {
    suspend fun createMeeting(createParam: CreateParam): CreateMeetingResult
}

fun Route.createMeeting(repository: CreateMeetingRepository) = post("/create") {
    val params = call.receive<CreateParam>()

    when (val result = repository.createMeeting(params)) {
        is CreateMeetingResult.Success -> call.respondSuccess(result.meeting)
        CreateMeetingResult.InvalidAccessIdentity -> call.respondFailure(Failure.InvalidToken)
        CreateMeetingResult.InvalidUtf8String -> call.respondFailure(Failure.InvalidUtf8String)
        CreateMeetingResult.InvalidFileIdentity -> call.respondFailure(Failure.InvalidFileIdentity)
    }
}
