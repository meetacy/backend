package app.meetacy.backend.database.integration.meetings.avatar

import app.meetacy.backend.database.meetings.MeetingsTable
import app.meetacy.backend.types.FileIdentity
import app.meetacy.backend.types.MeetingIdentity
import app.meetacy.backend.usecase.meetings.avatar.add.AddMeetingAvatarUsecase
import org.jetbrains.exposed.sql.Database

class DatabaseAddMeetingAvatarStorage(db: Database) : AddMeetingAvatarUsecase.Storage {
    private val meetingsTable = MeetingsTable(db)

    override suspend fun addAvatar(meetingIdentity: MeetingIdentity, avatarIdentity: FileIdentity) {
        meetingsTable.addAvatar(meetingIdentity.meetingId, avatarIdentity)
    }
}