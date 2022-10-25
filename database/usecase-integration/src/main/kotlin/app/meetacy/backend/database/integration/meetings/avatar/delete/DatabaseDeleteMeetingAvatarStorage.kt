package app.meetacy.backend.database.integration.meetings.avatar.delete

import app.meetacy.backend.database.meetings.MeetingsTable
import app.meetacy.backend.types.FileIdentity
import app.meetacy.backend.types.MeetingIdentity
import app.meetacy.backend.usecase.meetings.avatar.delete.DeleteMeetingAvatarUsecase
import org.jetbrains.exposed.sql.Database

class DatabaseDeleteMeetingAvatarStorage(db: Database) : DeleteMeetingAvatarUsecase.Storage {
    private val meetingsTable = MeetingsTable(db)

    override suspend fun deleteAvatar(meetingIdentity: MeetingIdentity, avatarIdentity: FileIdentity) {
        meetingsTable.deleteAvatar(meetingIdentity.meetingId, avatarIdentity)
    }
}