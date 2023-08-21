package app.meetacy.backend.feature.meetings.usecase.integration.create

import app.meetacy.backend.feature.meetings.endpoints.create.CreateMeetingRepository
import app.meetacy.backend.feature.meetings.endpoints.create.CreateMeetingResult
import app.meetacy.backend.feature.meetings.endpoints.create.CreateParam
import app.meetacy.backend.types.serializable.access.type
import app.meetacy.backend.types.serializable.datetime.type
import app.meetacy.backend.types.serializable.file.type
import app.meetacy.backend.types.serializable.location.type
import app.meetacy.backend.types.serializable.meetings.serializable
import app.meetacy.backend.types.serializable.meetings.typeFullMeeting
import app.meetacy.backend.feature.meetings.usecase.create.CreateMeetingUsecase

class UsecaseCreateMeetingRepository(
    private val usecase: CreateMeetingUsecase
) : CreateMeetingRepository {
    override suspend fun createMeeting(
        createParam: CreateParam
    ): CreateMeetingResult = with(createParam) {
        when (
            val result = usecase.createMeeting(
                token = token.type(),
                title = title,
                description = description,
                date = date.type(),
                location = location.type(),
                visibility = visibility.typeFullMeeting(),
                avatarIdentity = avatarId?.type()
            )
        ) {
            CreateMeetingUsecase.Result.TokenInvalid ->
                CreateMeetingResult.InvalidAccessIdentity
            is CreateMeetingUsecase.Result.Success ->
                CreateMeetingResult.Success(result.meeting.serializable())
            CreateMeetingUsecase.Result.InvalidUtf8String ->
                CreateMeetingResult.InvalidUtf8String
            CreateMeetingUsecase.Result.InvalidFileIdentity ->
                CreateMeetingResult.InvalidFileIdentity

        }
    }
}
