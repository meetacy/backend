package app.meetacy.backend.feature.meetings.endpoints.integration.create

import app.meetacy.backend.feature.meetings.endpoints.create.CreateMeetingRepository
import app.meetacy.backend.feature.meetings.endpoints.create.CreateMeetingResult
import app.meetacy.backend.feature.meetings.endpoints.create.createMeeting
import app.meetacy.backend.feature.meetings.usecase.create.CreateMeetingUsecase
import app.meetacy.backend.feature.meetings.usecase.create.CreateMeetingUsecase.Result
import app.meetacy.backend.types.serializable.access.AccessIdentity
import app.meetacy.backend.types.serializable.access.type
import app.meetacy.backend.types.serializable.datetime.Date
import app.meetacy.backend.types.serializable.datetime.type
import app.meetacy.backend.types.serializable.file.FileId
import app.meetacy.backend.types.serializable.file.type
import app.meetacy.backend.types.serializable.location.Location
import app.meetacy.backend.types.serializable.location.type
import app.meetacy.backend.types.serializable.meetings.*
import app.meetacy.di.DI
import io.ktor.server.routing.*

internal fun Route.createMeeting(di: DI) {
    val createMeetingUsecase: CreateMeetingUsecase by di.getting

    val repository = object : CreateMeetingRepository {
        override suspend fun createMeeting(
            token: AccessIdentity,
            title: MeetingTitle,
            description: MeetingDescription?,
            date: Date,
            location: Location,
            visibility: Meeting.Visibility,
            avatarId: FileId?
        ): CreateMeetingResult = when (
            val result =
                createMeetingUsecase.createMeeting(
                    token = token.type(),
                    title = title.type(),
                    description = description?.type(),
                    date = date.type(),
                    location = location.type(),
                    visibility = visibility.typeFullMeeting(),
                    avatarIdentity = avatarId?.type()
                )

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
