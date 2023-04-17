package app.meetacy.backend.database.integration.meetings.create

import app.meetacy.backend.database.integration.meetings.participate.DatabaseViewMeetingsUsecaseStorage
import app.meetacy.backend.database.integration.types.DatabaseFilesRepository
import app.meetacy.backend.database.integration.types.DatabaseGetUsersViewsRepository
import app.meetacy.backend.database.integration.types.mapToDatabase
import app.meetacy.backend.database.meetings.MeetingsTable
import app.meetacy.backend.database.meetings.ParticipantsTable
import app.meetacy.backend.types.access.AccessHash
import app.meetacy.backend.types.datetime.Date
import app.meetacy.backend.types.file.FileId
import app.meetacy.backend.types.location.Location
import app.meetacy.backend.types.meeting.MeetingId
import app.meetacy.backend.types.meeting.MeetingIdentity
import app.meetacy.backend.types.user.UserId
import app.meetacy.backend.usecase.meetings.create.CreateMeetingUsecase
import app.meetacy.backend.usecase.meetings.get.ViewMeetingsUsecase
import app.meetacy.backend.usecase.types.FullMeeting
import app.meetacy.backend.usecase.types.MeetingView
import org.jetbrains.exposed.sql.Database

class DatabaseCreateMeetingStorage(db: Database) : CreateMeetingUsecase.Storage {
    private val meetingsTable = MeetingsTable(db)
    private val participantsTable = ParticipantsTable(db)
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
        val meetingId = meetingsTable.addMeeting(
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
        participantsTable.addParticipant(participantId, meetingId)
    }
}

class DatabaseCreateMeetingViewMeetingRepository(private val db: Database) : CreateMeetingUsecase.ViewMeetingRepository {
    override suspend fun viewMeeting(
        viewer: UserId,
        meeting: FullMeeting
    ): MeetingView = ViewMeetingsUsecase(DatabaseGetUsersViewsRepository(db), DatabaseFilesRepository(db), DatabaseViewMeetingsUsecaseStorage(db))
        .viewMeetings(viewer, meetings = listOf(meeting)).first()
}
