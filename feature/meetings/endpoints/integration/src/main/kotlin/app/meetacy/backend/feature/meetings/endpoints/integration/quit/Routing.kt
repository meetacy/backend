package app.meetacy.backend.feature.meetings.endpoints.integration.quit

import app.meetacy.backend.feature.meetings.endpoints.quit.QuitMeetingRepository
import app.meetacy.backend.feature.meetings.endpoints.quit.QuitMeetingResult
import app.meetacy.backend.feature.meetings.endpoints.quit.quitMeeting
import app.meetacy.backend.feature.meetings.usecase.quit.QuitMeetingUsecase
import app.meetacy.backend.types.serializable.access.AccessIdentity
import app.meetacy.backend.types.serializable.access.type
import app.meetacy.backend.types.serializable.meetings.MeetingId
import app.meetacy.backend.types.serializable.meetings.type
import app.meetacy.di.DI
import io.ktor.server.routing.*

fun Route.quitMeeting(di: DI) {
    val quitMeetingUsecase: QuitMeetingUsecase by di.getting

    val repository = object : QuitMeetingRepository {
        override suspend fun quitMeeting(
            token: AccessIdentity, meetingId: MeetingId
        ): QuitMeetingResult =
            when (
                quitMeetingUsecase.quitMeeting(
                    accessIdentity = token.type(),
                    meetingIdentity = meetingId.type()
                )
            ) {
                QuitMeetingUsecase.Result.InvalidIdentity -> QuitMeetingResult.InvalidIdentity
                QuitMeetingUsecase.Result.MeetingNotFound -> QuitMeetingResult.MeetingNotFound
                QuitMeetingUsecase.Result.Success -> QuitMeetingResult.Success
            }

    }
    quitMeeting(repository)
}
