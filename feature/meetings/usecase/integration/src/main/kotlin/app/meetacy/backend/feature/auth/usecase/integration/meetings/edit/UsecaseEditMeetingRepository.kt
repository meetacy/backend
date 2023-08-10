package app.meetacy.backend.feature.auth.usecase.integration.meetings.edit

import app.meetacy.backend.endpoint.meetings.edit.EditMeetingParams
import app.meetacy.backend.endpoint.meetings.edit.EditMeetingRepository
import app.meetacy.backend.endpoint.meetings.edit.EditMeetingResult
import app.meetacy.backend.types.optional.map
import app.meetacy.backend.types.serializable.access.type
import app.meetacy.backend.types.serializable.datetime.type
import app.meetacy.backend.types.serializable.file.type
import app.meetacy.backend.types.serializable.location.type
import app.meetacy.backend.types.serializable.meeting.type
import app.meetacy.backend.types.serializable.optional.type
import app.meetacy.backend.usecase.integration.types.mapToEndpoint
import app.meetacy.backend.usecase.integration.types.mapToFullMeeting
import app.meetacy.backend.usecase.meetings.edit.EditMeetingUsecase

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
                visibility?.mapToFullMeeting()
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
                EditMeetingResult.Success(result.meeting.mapToEndpoint())
        }
    }
}
