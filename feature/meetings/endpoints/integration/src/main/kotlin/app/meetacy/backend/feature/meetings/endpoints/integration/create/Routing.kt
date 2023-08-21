package app.meetacy.backend.feature.meetings.endpoints.integration.create

import app.meetacy.backend.feature.meetings.endpoints.create.CreateMeetingRepository
import app.meetacy.backend.feature.meetings.endpoints.create.CreateMeetingResult
import app.meetacy.backend.feature.meetings.endpoints.create.CreateParam
import app.meetacy.backend.feature.meetings.endpoints.create.createMeeting
import app.meetacy.backend.feature.meetings.usecase.create.CreateMeetingUsecase
import app.meetacy.backend.feature.meetings.usecase.create.CreateMeetingUsecase.Result
import app.meetacy.backend.types.serializable.access.type
import app.meetacy.backend.types.serializable.datetime.type
import app.meetacy.backend.types.serializable.file.type
import app.meetacy.backend.types.serializable.location.type
import app.meetacy.backend.types.serializable.meetings.serializable
import app.meetacy.backend.types.serializable.meetings.typeFullMeeting
import app.meetacy.di.global.di
import io.ktor.server.routing.*

internal fun Route.createMeeting() {
    val createMeetingUsecase: CreateMeetingUsecase by di.getting

    val repository = object : CreateMeetingRepository {
        override suspend fun createMeeting(
            createParam: CreateParam
        ): CreateMeetingResult = when (
            val result = with (createParam) {
                createMeetingUsecase.createMeeting(
                    token = token.type(),
                    title = title,
                    description = description,
                    date = date.type(),
                    location = location.type(),
                    visibility = visibility.typeFullMeeting(),
                    avatarIdentity = avatarId?.type()
                )
            }
        ) {
            Result.InvalidFileIdentity -> CreateMeetingResult.InvalidFileIdentity
            Result.InvalidUtf8String -> CreateMeetingResult.InvalidUtf8String
            Result.TokenInvalid -> CreateMeetingResult.InvalidAccessIdentity
            is Result.Success -> CreateMeetingResult.Success(
                meeting = result.meeting.serializable()
            )
        }
    }

    createMeeting(repository)
}
