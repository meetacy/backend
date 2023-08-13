package app.meetacy.feature.meetings.database.integration.meetings

import app.meetacy.backend.feature.meetings.database.meetings.MeetingsStorage
import app.meetacy.backend.types.meetings.CheckMeetingRepository
import app.meetacy.backend.types.meetings.MeetingIdentity
import org.jetbrains.exposed.sql.Database

class DatabaseCheckMeetingsViewRepository(db: Database) : CheckMeetingRepository {
    private val meetingsStorage = MeetingsStorage(db)

    override suspend fun checkMeetingIdentity(identity: MeetingIdentity): Boolean =
        meetingsStorage.getMeeting(identity.id).identity == identity
}
