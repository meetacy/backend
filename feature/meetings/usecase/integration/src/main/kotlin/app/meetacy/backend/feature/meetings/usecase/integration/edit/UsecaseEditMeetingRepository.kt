package app.meetacy.backend.feature.meetings.usecase.integration.edit

import app.meetacy.backend.feature.meetings.endpoints.edit.EditMeetingParams
import app.meetacy.backend.feature.meetings.endpoints.edit.EditMeetingRepository
import app.meetacy.backend.feature.meetings.endpoints.edit.EditMeetingResult
import app.meetacy.backend.types.optional.map
import app.meetacy.backend.types.serializable.access.type
import app.meetacy.backend.types.serializable.datetime.type
import app.meetacy.backend.types.serializable.file.type
import app.meetacy.backend.types.serializable.location.type
import app.meetacy.backend.types.serializable.meetings.type
import app.meetacy.backend.types.serializable.meetings.typeFullMeeting
import app.meetacy.backend.types.serializable.optional.type
import app.meetacy.backend.feature.meetings.usecase.edit.EditMeetingUsecase

class UsecaseEditMeetingRepository(
    private val usecase: EditMeetingUsecase
) : EditMeetingRepository {

    override suspend fun editMeeting(
        editMeetingParams: EditMeetingParams
    ): EditMeetingResult = with(editMeetingParams) {
        when (
            val result = usecase.editMeeting(
                token.type(),
                meetingId.type()!!,
                avatarId.type().map { fileIdentity -> fileIdentity?.type() },
                title,
                description,
                location?.type(),
                date?.type(),
                visibility?.typeFullMeeting()
            )
        ) {
            EditMeetingUsecase.Result.InvalidAccessIdentity ->
                EditMeetingResult.InvalidAccessIdentity
            EditMeetingUsecase.Result.InvalidAvatarIdentity ->
                EditMeetingResult.InvalidAvatarIdentity
            EditMeetingUsecase.Result.InvalidMeetingIdentity ->
                EditMeetingResult.InvalidMeetingId
            EditMeetingUsecase.Result.InvalidUtf8String ->
                EditMeetingResult.InvalidUtf8String
            EditMeetingUsecase.Result.NullEditParameters ->
                EditMeetingResult.NullEditParameters
            is EditMeetingUsecase.Result.Success ->
                EditMeetingResult.Success(result.meeting.type())
        }
    }
}
