package app.meetacy.backend.feature.meetings.endpoints.integration.delete

import app.meetacy.backend.feature.meetings.endpoints.delete.DeleteMeetingParams
import app.meetacy.backend.feature.meetings.endpoints.delete.DeleteMeetingRepository
import app.meetacy.backend.feature.meetings.endpoints.delete.DeleteMeetingResult
import app.meetacy.backend.feature.meetings.endpoints.delete.deleteMeeting
import app.meetacy.backend.feature.meetings.usecase.delete.DeleteMeetingUsecase
import app.meetacy.backend.feature.meetings.usecase.delete.DeleteMeetingUsecase.Result
import app.meetacy.backend.types.serializable.access.type
import app.meetacy.backend.types.serializable.meetings.type
import app.meetacy.di.DI
import io.ktor.server.routing.*

fun Route.deleteMeeting(di: DI) {
    val deleteMeetingUsecase: DeleteMeetingUsecase by di.getting

    val repository = object : DeleteMeetingRepository {
        override suspend fun deleteMeeting(
            deleteMeetingParams: DeleteMeetingParams
        ): DeleteMeetingResult = with(deleteMeetingParams) {
            when (
                deleteMeetingUsecase.deleteMeeting(
                    accessIdentity = token.type(),
                    meetingIdentity = meetingId.type()
                )
            ) {
                Result.InvalidIdentity -> DeleteMeetingResult.InvalidIdentity
                Result.MeetingNotFound -> DeleteMeetingResult.MeetingNotFound
                Result.Success -> DeleteMeetingResult.Success
            }
        }
    }

    deleteMeeting(repository)
}
