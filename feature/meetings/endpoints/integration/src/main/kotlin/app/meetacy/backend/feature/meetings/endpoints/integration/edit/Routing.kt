package app.meetacy.backend.feature.meetings.endpoints.integration.edit

import app.meetacy.backend.feature.meetings.endpoints.edit.EditMeetingParams
import app.meetacy.backend.feature.meetings.endpoints.edit.EditMeetingRepository
import app.meetacy.backend.feature.meetings.endpoints.edit.EditMeetingResult
import app.meetacy.backend.feature.meetings.endpoints.edit.editMeeting
import app.meetacy.backend.feature.meetings.usecase.edit.EditMeetingUsecase
import app.meetacy.backend.feature.meetings.usecase.edit.EditMeetingUsecase.Result
import app.meetacy.backend.types.optional.map
import app.meetacy.backend.types.serializable.access.type
import app.meetacy.backend.types.serializable.datetime.type
import app.meetacy.backend.types.serializable.file.type
import app.meetacy.backend.types.serializable.location.type
import app.meetacy.backend.types.serializable.meetings.serializable
import app.meetacy.backend.types.serializable.meetings.type
import app.meetacy.backend.types.serializable.meetings.typeFullMeeting
import app.meetacy.backend.types.serializable.optional.type
import app.meetacy.di.global.di
import io.ktor.server.routing.*

fun Route.editMeeting() {
    val editMeetingUsecase: EditMeetingUsecase by di.getting

    val repository = object : EditMeetingRepository {
        override suspend fun editMeeting(
            editMeetingParams: EditMeetingParams
        ): EditMeetingResult = with (editMeetingParams) {
            when (
                val result = editMeetingUsecase.editMeeting(
                    token = token.type(),
                    meetingIdentity = meetingId.type(),
                    avatarIdentityOptional = avatarId.type().map { it?.type() },
                    title = title,
                    description = description,
                    location = location?.type(),
                    date = date?.type(),
                    visibility = visibility?.typeFullMeeting()
                )
            ) {
                Result.InvalidAccessIdentity -> EditMeetingResult.InvalidAccessIdentity
                Result.InvalidAvatarIdentity -> EditMeetingResult.InvalidAvatarIdentity
                Result.InvalidMeetingIdentity -> EditMeetingResult.InvalidMeetingId
                Result.InvalidUtf8String -> EditMeetingResult.InvalidUtf8String
                Result.NullEditParameters -> EditMeetingResult.NullEditParameters
                is Result.Success -> EditMeetingResult.Success(result.meeting.serializable())
            }
        }
    }

    editMeeting(repository)
}
