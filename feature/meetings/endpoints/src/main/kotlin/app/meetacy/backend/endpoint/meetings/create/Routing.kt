
package app.meetacy.backend.endpoint.meetings.create

import app.meetacy.backend.endpoint.ktor.Failure
import app.meetacy.backend.endpoint.ktor.respondFailure
import app.meetacy.backend.endpoint.ktor.respondSuccess
import app.meetacy.backend.types.serializable.access.AccessIdentity
import app.meetacy.backend.types.serializable.datetime.Date
import app.meetacy.backend.types.serializable.file.FileIdentity
import app.meetacy.backend.types.serializable.location.Location
import app.meetacy.backend.types.serializable.meetings.Meeting
import app.meetacy.di.global.di
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
    class Success(val meeting: Meeting) : CreateMeetingResult
    object InvalidAccessIdentity : CreateMeetingResult
    object InvalidUtf8String : CreateMeetingResult
    object InvalidFileIdentity : CreateMeetingResult
}

interface CreateMeetingRepository {
    suspend fun createMeeting(createParam: CreateParam): CreateMeetingResult
}

fun Route.createMeeting() = post("/create") {
    val createMeetingRepository: CreateMeetingRepository by di.getting

    val params = call.receive<CreateParam>()

    when (val result = createMeetingRepository.createMeeting(params)) {
        is CreateMeetingResult.Success -> call.respondSuccess(result.meeting)
        CreateMeetingResult.InvalidAccessIdentity -> call.respondFailure(Failure.InvalidToken)
        CreateMeetingResult.InvalidUtf8String -> call.respondFailure(Failure.InvalidUtf8String)
        CreateMeetingResult.InvalidFileIdentity -> call.respondFailure(Failure.InvalidFileIdentity)
    }
}
