package app.meetacy.backend.database.integration.meetings

import app.meetacy.backend.database.meetings.MeetingsStorage
import app.meetacy.backend.feature.auth.usecase.types.CheckMeetingRepository
import app.meetacy.backend.types.meeting.MeetingIdentity
import org.jetbrains.exposed.sql.Database

class DatabaseCheckMeetingsViewRepository(db: Database) : CheckMeetingRepository {
    private val meetingsStorage = MeetingsStorage(db)

    override suspend fun checkMeetingIdentity(identity: MeetingIdentity): Boolean =
        meetingsStorage.getMeeting(identity.id).identity == identity
}
