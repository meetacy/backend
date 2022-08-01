package app.meetacy.backend.usecase.integration.meetings

import app.meetacy.backend.endpoint.meetings.create.CreateMeetingRepository
import app.meetacy.backend.endpoint.meetings.create.CreateMeetingResult
import app.meetacy.backend.endpoint.meetings.create.CreateParam
import app.meetacy.backend.usecase.integration.types.mapToEndpoint
import app.meetacy.backend.usecase.meetings.CreateMeetingUsecase

private class CreateMeetingIntegration(
    private val usecase: CreateMeetingUsecase
): CreateMeetingRepository {
    override suspend fun createMeeting(
        createParam: CreateParam
    ): CreateMeetingResult = with(createParam) {
        when (val result = usecase.createMeeting(accessToken, title, description, date, location)) {
            CreateMeetingUsecase.Result.TokenInvalid ->
                CreateMeetingResult.TokenInvalid
            is CreateMeetingUsecase.Result.Success ->
                CreateMeetingResult.Success(result.meeting.mapToEndpoint())
        }
    }
}

fun usecaseCreateMeetingRepository(usecase: CreateMeetingUsecase): CreateMeetingRepository =
    CreateMeetingIntegration(usecase)
