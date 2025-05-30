package app.meetacy.backend.feature.meetings.endpoints.integration.participate

import app.meetacy.backend.feature.meetings.endpoints.participate.ParticipateMeetingRepository
import app.meetacy.backend.feature.meetings.endpoints.participate.ParticipateMeetingResult
import app.meetacy.backend.feature.meetings.endpoints.participate.participateMeeting
import app.meetacy.backend.feature.meetings.usecase.participate.ParticipateMeetingUsecase
import app.meetacy.backend.feature.meetings.usecase.participate.ParticipateMeetingUsecase.Result
import app.meetacy.backend.types.serializable.access.AccessIdentity
import app.meetacy.backend.types.serializable.access.type
import app.meetacy.backend.types.serializable.meetings.MeetingId
import app.meetacy.backend.types.serializable.meetings.type
import app.meetacy.di.DI
import io.ktor.server.routing.*

fun Route.participateMeeting(di: DI) {
    val participateMeetingUsecase: ParticipateMeetingUsecase by di.getting

    val repository = object : ParticipateMeetingRepository {
        override suspend fun participateMeeting(
            meetingId: MeetingId,
            accessIdentity: AccessIdentity
        ): ParticipateMeetingResult = when (
            participateMeetingUsecase.participateMeeting(
                meetingIdentity = meetingId.type(),
                accessIdentity = accessIdentity.type()
            )
        ) {
            Result.AlreadyParticipant -> ParticipateMeetingResult.AlreadyParticipant
            Result.MeetingNotFound -> ParticipateMeetingResult.MeetingNotFound
            Result.TokenInvalid -> ParticipateMeetingResult.InvalidIdentity
            Result.Success -> ParticipateMeetingResult.Success
        }
    }

    participateMeeting(repository)
}
