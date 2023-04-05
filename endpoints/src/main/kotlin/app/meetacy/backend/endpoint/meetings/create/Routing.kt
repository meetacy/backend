package app.meetacy.backend.endpoint.meetings.create

import app.meetacy.backend.endpoint.ktor.Failure
import app.meetacy.backend.endpoint.ktor.respondFailure
import app.meetacy.backend.endpoint.ktor.respondSuccess
import app.meetacy.backend.endpoint.types.Meeting
import app.meetacy.backend.types.serialization.access.AccessIdentitySerializable
import app.meetacy.backend.types.serialization.datetime.DateOrTimeSerializable
import app.meetacy.backend.types.serialization.datetime.DateSerializable
import app.meetacy.backend.types.serialization.location.LocationSerializable
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable

@Serializable
data class CreateParam(
    val accessIdentity: AccessIdentitySerializable,
    val title: String?,
    val description: String?,
    val date: DateOrTimeSerializable,
    val location: LocationSerializable,
    val visibility: Meeting.Visibility
)

sealed interface CreateMeetingResult {
    class Success(val meeting: Meeting) : CreateMeetingResult
    object InvalidAccessIdentity : CreateMeetingResult
    object InvalidUtf8String : CreateMeetingResult
}

interface CreateMeetingRepository {
    suspend fun createMeeting(createParam: CreateParam): CreateMeetingResult
}

fun Route.createMeeting(createMeetingRepository: CreateMeetingRepository) = post("/create") {
    val params = call.receive<CreateParam>()

    when (val result = createMeetingRepository.createMeeting(params)) {
        is CreateMeetingResult.Success -> call.respondSuccess(result.meeting)
        CreateMeetingResult.InvalidAccessIdentity -> call.respondFailure(Failure.InvalidAccessIdentity)
        CreateMeetingResult.InvalidUtf8String -> call.respondFailure(Failure.InvalidTitleOrDescription)
    }
}
