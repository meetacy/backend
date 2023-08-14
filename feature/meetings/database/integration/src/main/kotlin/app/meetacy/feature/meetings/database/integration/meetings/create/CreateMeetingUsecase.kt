package app.meetacy.feature.meetings.database.integration.meetings.create

import app.meetacy.feature.meetings.database.integration.meetings.participate.DatabaseViewMeetingsUsecaseStorage
import app.meetacy.backend.feature.files.database.integration.types.DatabaseFilesRepository
import app.meetacy.backend.feature.users.database.integration.types.DatabaseGetUsersViewsRepository
import app.meetacy.feature.meetings.database.integration.types.mapToDatabase
import app.meetacy.backend.feature.meetings.database.meetings.MeetingsStorage
import app.meetacy.backend.feature.meetings.database.meetings.ParticipantsStorage
import app.meetacy.backend.types.access.AccessHash
import app.meetacy.backend.types.datetime.Date
import app.meetacy.backend.types.file.FileId
import app.meetacy.backend.types.location.Location
import app.meetacy.backend.types.meetings.FullMeeting
import app.meetacy.backend.types.meetings.MeetingId
import app.meetacy.backend.types.meetings.MeetingIdentity
import app.meetacy.backend.types.meetings.MeetingView
import app.meetacy.backend.types.users.UserId
import app.meetacy.backend.feature.meetings.usecase.create.CreateMeetingUsecase
import app.meetacy.backend.feature.meetings.usecase.get.ViewMeetingsUsecase
import org.jetbrains.exposed.sql.Database

class DatabaseCreateMeetingStorage(db: Database) : CreateMeetingUsecase.Storage {
    private val meetingsStorage = MeetingsStorage(db)
    private val participantsStorage = ParticipantsStorage(db)
    override suspend fun addMeeting(
        accessHash: AccessHash,
        creatorId: UserId,
        date: Date,
        location: Location,
        title: String?,
        description: String?,
        visibility: FullMeeting.Visibility,
        avatarId: FileId?
    ): FullMeeting {
        val meetingId = meetingsStorage.addMeeting(
            accessHash = accessHash,
            creatorId = creatorId,
            date = date,
            location = location,
            title = title,
            description = description,
            visibility = visibility.mapToDatabase(),
            avatarId = avatarId
        )
        return FullMeeting(
            identity = MeetingIdentity(meetingId, accessHash),
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

class DatabaseCreateMeetingViewMeetingRepository(private val db: Database) : CreateMeetingUsecase.ViewMeetingRepository {
    override suspend fun viewMeeting(
        viewer: UserId,
        meeting: FullMeeting
    ): MeetingView = ViewMeetingsUsecase(DatabaseGetUsersViewsRepository(db), DatabaseFilesRepository(db), DatabaseViewMeetingsUsecaseStorage(db))
        .viewMeetings(viewer, meetings = listOf(meeting)).first()
}
