package app.meetacy.backend.usecase.integration.meetings.create

import app.meetacy.backend.endpoint.meetings.create.CreateMeetingRepository
import app.meetacy.backend.endpoint.meetings.create.CreateMeetingResult
import app.meetacy.backend.endpoint.meetings.create.CreateParam
import app.meetacy.backend.types.serializable.access.type
import app.meetacy.backend.types.serializable.datetime.type
import app.meetacy.backend.types.serializable.location.type
import app.meetacy.backend.usecase.integration.types.mapToEndpoint
import app.meetacy.backend.usecase.integration.types.mapToFullMeeting
import app.meetacy.backend.usecase.meetings.create.CreateMeetingUsecase

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
                visibility = visibility.mapToFullMeeting(),
                avatarIdentity = avatarId?.type()
            )
        ) {
            CreateMeetingUsecase.Result.TokenInvalid ->
                CreateMeetingResult.InvalidAccessIdentity
            is CreateMeetingUsecase.Result.Success ->
                CreateMeetingResult.Success(result.meeting.mapToEndpoint())
            CreateMeetingUsecase.Result.InvalidUtf8String ->
                CreateMeetingResult.InvalidUtf8String
            CreateMeetingUsecase.Result.InvalidFileIdentity ->
                CreateMeetingResult.InvalidFileIdentity

        }
    }
}
