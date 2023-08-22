package app.meetacy.backend.feature.meetings.endpoints.integration.get

import app.meetacy.backend.feature.meetings.endpoints.get.GetMeetingRepository
import app.meetacy.backend.feature.meetings.endpoints.get.GetMeetingResult
import app.meetacy.backend.feature.meetings.endpoints.get.getMeeting
import app.meetacy.backend.feature.meetings.usecase.get.GetMeetingUsecase
import app.meetacy.backend.feature.meetings.usecase.get.GetMeetingUsecase.Result
import app.meetacy.backend.types.serializable.access.AccessIdentity
import app.meetacy.backend.types.serializable.access.type
import app.meetacy.backend.types.serializable.meetings.MeetingIdentity
import app.meetacy.backend.types.serializable.meetings.serializable
import app.meetacy.backend.types.serializable.meetings.type
import app.meetacy.di.global.di
import io.ktor.server.routing.*

fun Route.getMeeting()  {
    val getMeetingUsecase: GetMeetingUsecase by di.getting

    val repository = object : GetMeetingRepository {
        override suspend fun getMeeting(
            accessIdentity: AccessIdentity,
            meetingIdentity: MeetingIdentity
        ): GetMeetingResult = when (
            val result = getMeetingUsecase.getMeeting(
                accessIdentity = accessIdentity.type(),
                meetingIdentity = meetingIdentity.type()
            )
        ) {
            Result.MeetingNotFound -> GetMeetingResult.MeetingNotFound
            Result.TokenInvalid -> GetMeetingResult.InvalidAccessIdentity
            is Result.Success -> GetMeetingResult.Success(result.meeting.serializable())
        }
    }

    getMeeting(repository)
}
