package app.meetacy.backend.feature.meetings.endpoints.integration.leave

import app.meetacy.backend.feature.meetings.endpoints.leave.LeaveMeetingRepository
import app.meetacy.backend.feature.meetings.endpoints.leave.LeaveMeetingResult
import app.meetacy.backend.feature.meetings.endpoints.leave.leaveMeeting
import app.meetacy.backend.feature.meetings.usecase.leave.LeaveMeetingUsecase
import app.meetacy.backend.types.serializable.access.AccessIdentity
import app.meetacy.backend.types.serializable.access.type
import app.meetacy.backend.types.serializable.meetings.MeetingId
import app.meetacy.backend.types.serializable.meetings.type
import app.meetacy.di.DI
import io.ktor.server.routing.*

fun Route.leaveMeeting(di: DI) {
    val leaveMeetingUsecase: LeaveMeetingUsecase by di.getting

    val repository = object : LeaveMeetingRepository {
        override suspend fun leaveMeeting(
            token: AccessIdentity, meetingId: MeetingId
        ): LeaveMeetingResult =
            when (
                leaveMeetingUsecase.leaveMeeting(
                    accessIdentity = token.type(),
                    meetingIdentity = meetingId.type()
                )
            ) {
                LeaveMeetingUsecase.Result.InvalidIdentity -> LeaveMeetingResult.InvalidIdentity
                LeaveMeetingUsecase.Result.MeetingNotFound -> LeaveMeetingResult.MeetingNotFound
                LeaveMeetingUsecase.Result.Success -> LeaveMeetingResult.Success
            }

    }
    this@leaveMeeting.leaveMeeting(repository)
}
