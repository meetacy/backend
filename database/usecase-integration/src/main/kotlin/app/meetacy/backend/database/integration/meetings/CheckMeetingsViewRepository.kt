package app.meetacy.backend.database.integration.meetings

import app.meetacy.backend.database.meetings.MeetingsStorage
import app.meetacy.backend.types.meeting.MeetingIdentity
import app.meetacy.backend.usecase.types.CheckMeetingRepository
import org.jetbrains.exposed.sql.Database

class DatabaseCheckMeetingsViewRepository(db: Database) : CheckMeetingRepository {
    private val meetingsStorage = MeetingsStorage(db)

    override suspend fun checkMeetingIdentity(identity: MeetingIdentity): Boolean =
        meetingsStorage.getMeeting(identity.id).identity == identity
}
