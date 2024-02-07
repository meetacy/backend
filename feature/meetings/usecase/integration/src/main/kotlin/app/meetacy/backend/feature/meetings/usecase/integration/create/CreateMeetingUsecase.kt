package app.meetacy.backend.feature.meetings.usecase.integration.create

import app.meetacy.backend.feature.meetings.database.meetings.MeetingsStorage
import app.meetacy.backend.feature.meetings.database.meetings.ParticipantsStorage
import app.meetacy.backend.feature.meetings.usecase.create.CreateMeetingUsecase
import app.meetacy.backend.types.access.AccessHash
import app.meetacy.backend.types.auth.AuthRepository
import app.meetacy.backend.types.datetime.Date
import app.meetacy.backend.types.description.Description
import app.meetacy.backend.types.files.FileId
import app.meetacy.backend.types.files.FilesRepository
import app.meetacy.backend.types.generator.AccessHashGenerator
import app.meetacy.backend.types.location.Location
import app.meetacy.backend.types.meetings.FullMeeting
import app.meetacy.backend.types.meetings.MeetingId
import app.meetacy.backend.types.meetings.MeetingIdentity
import app.meetacy.backend.types.meetings.ViewMeetingsRepository
import app.meetacy.backend.types.title.Title
import app.meetacy.backend.types.users.UserId
import app.meetacy.backend.types.utf8Checker.Utf8Checker
import app.meetacy.di.builder.DIBuilder

internal fun DIBuilder.createMeetingUsecase() {
    val createMeetingUsecase by singleton {
        val accessHashGenerator: AccessHashGenerator by getting
        val authRepository: AuthRepository by getting
        val filesRepository: FilesRepository by getting
        val viewMeetingsRepository: ViewMeetingsRepository by getting
        val utf8Checker: Utf8Checker by getting

        val meetingsStorage: MeetingsStorage by getting
        val participantsStorage: ParticipantsStorage by getting

        val storage = object : CreateMeetingUsecase.Storage {
            override suspend fun addMeeting(
                accessHash: AccessHash,
                creatorId: UserId,
                date: Date,
                location: Location,
                title: Title,
                description: Description?,
                visibility: FullMeeting.Visibility,
                avatarId: FileId?
            ): FullMeeting {
                val id = meetingsStorage.addMeeting(
                    accessHash = accessHash,
                    creatorId = creatorId,
                    date = date,
                    location = location,
                    title = title,
                    description = description,
                    visibility = visibility,
                    avatarId = avatarId
                )

                val identity = MeetingIdentity(id, accessHash)

                return FullMeeting(
                    identity = identity,
                    creatorId = creatorId,
                    date = date,
                    location = location,
                    title = title,
                    description = description,
                    avatarId = avatarId,
                    visibility = visibility
                )
            }

            override suspend fun addParticipant(participantId: UserId, meetingId: MeetingId) {
                participantsStorage.addParticipant(participantId, meetingId)
            }
        }

        CreateMeetingUsecase(
            hashGenerator = accessHashGenerator,
            storage = storage,
            authRepository = authRepository,
            filesRepository = filesRepository,
            viewMeetingsRepository = viewMeetingsRepository,
            utf8Checker = utf8Checker
        )
    }
}
