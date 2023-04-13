package app.meetacy.backend.database.integration.meetings.create

import app.meetacy.backend.database.integration.meetings.participate.DatabaseViewMeetingsUsecaseStorage
import app.meetacy.backend.database.integration.types.DatabaseGetUsersViewsRepository
import app.meetacy.backend.database.integration.types.mapToDatabase
import app.meetacy.backend.database.meetings.MeetingsTable
import app.meetacy.backend.database.meetings.ParticipantsTable
import app.meetacy.backend.types.access.AccessHash
import app.meetacy.backend.types.datetime.Date
import app.meetacy.backend.types.location.Location
import app.meetacy.backend.types.meeting.IdMeeting
import app.meetacy.backend.types.meeting.MeetingIdentity
import app.meetacy.backend.types.user.UserId
import app.meetacy.backend.usecase.meetings.create.CreateMeetingUsecase
import app.meetacy.backend.usecase.meetings.get.ViewMeetingsUsecase
import app.meetacy.backend.usecase.types.FullMeeting
import app.meetacy.backend.usecase.types.MeetingView
import org.jetbrains.exposed.sql.Database

class DatabaseCreateMeetingStorage(private val db: Database) : CreateMeetingUsecase.Storage {
    private val meetingsTable = MeetingsTable(db)
    private val participantsTable = ParticipantsTable(db)

    override suspend fun addParticipant(participantId: UserId, idMeeting: IdMeeting) {
        participantsTable.addParticipant(participantId, idMeeting)
    }

    override suspend fun addMeeting(
        accessHash: AccessHash,
        creatorId: UserId,
        date: Date,
        location: Location,
        title: String?,
        description: String?,
        visibility: FullMeeting.Visibility
    ): FullMeeting {
        val meetingId = meetingsTable.addMeeting(
            accessHash = accessHash,
            creatorId = creatorId,
            date = date,
            location = location,
            title = title,
            description = description,
            visibility = visibility.mapToDatabase()
        )
        return FullMeeting(
            identity = MeetingIdentity(meetingId, accessHash),
            creatorId = creatorId,
            date = date,
            location = location,
            title = title,
            description = description,
            avatarIdentity = null,
            visibility = visibility
        )
    }
}

class DatabaseCreateMeetingViewMeetingRepository(private val db: Database) : CreateMeetingUsecase.ViewMeetingRepository {
    override suspend fun viewMeeting(
        viewer: UserId,
        meeting: FullMeeting
    ): MeetingView = ViewMeetingsUsecase(DatabaseGetUsersViewsRepository(db), DatabaseViewMeetingsUsecaseStorage(db))
        .viewMeetings(viewer, meetings = listOf(meeting)).first()
}
