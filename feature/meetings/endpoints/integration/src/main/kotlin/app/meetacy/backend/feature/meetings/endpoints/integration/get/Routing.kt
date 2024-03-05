package app.meetacy.backend.feature.meetings.endpoints.integration.get

import app.meetacy.backend.feature.meetings.endpoints.get.GetMeetingRepository
import app.meetacy.backend.feature.meetings.endpoints.get.GetMeetingResult
import app.meetacy.backend.feature.meetings.endpoints.get.getMeeting
import app.meetacy.backend.feature.meetings.usecase.get.GetMeetingUsecase
import app.meetacy.backend.feature.meetings.usecase.get.GetMeetingUsecase.Result
import app.meetacy.backend.types.serializable.access.AccessIdentity
import app.meetacy.backend.types.serializable.access.type
import app.meetacy.backend.types.serializable.meetings.MeetingId
import app.meetacy.backend.types.serializable.meetings.serializable
import app.meetacy.backend.types.serializable.meetings.type
import app.meetacy.di.DI
import io.ktor.server.routing.*

fun Route.getMeeting(di: DI)  {
    val getMeetingUsecase: GetMeetingUsecase by di.getting

    val repository = object : GetMeetingRepository {
        override suspend fun getMeeting(
            accessIdentity: AccessIdentity,
            meetingId: MeetingId
        ): GetMeetingResult = when (
            val result = getMeetingUsecase.getMeeting(
                accessIdentity = accessIdentity.type(),
                meetingIdentity = meetingId.type()
            )
        ) {
            Result.MeetingNotFound -> GetMeetingResult.MeetingNotFound
            Result.TokenInvalid -> GetMeetingResult.InvalidAccessIdentity
            is Result.Success -> GetMeetingResult.Success(result.meeting.serializable())
        }
    }

    getMeeting(repository)
}
