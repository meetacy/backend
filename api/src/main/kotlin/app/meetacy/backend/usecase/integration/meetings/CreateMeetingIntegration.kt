package app.meetacy.backend.usecase.integration.meetings

import app.meetacy.backend.endpoint.meetings.create.CreateMeetingRepository
import app.meetacy.backend.endpoint.meetings.create.CreateMeetingResult
import app.meetacy.backend.endpoint.meetings.create.CreateParam
import app.meetacy.backend.endpoint.types.Meeting
import app.meetacy.backend.endpoint.types.User
import app.meetacy.backend.usecase.meetings.CreateMeetingUsecase

private class Integration(
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
