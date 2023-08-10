package app.meetacy.backend.database.integration.meetings.participate

import app.meetacy.backend.database.meetings.ParticipantsStorage
import app.meetacy.backend.types.meetings.MeetingId
import app.meetacy.backend.types.users.UserId
import app.meetacy.backend.usecase.meetings.participate.ParticipateMeetingUsecase
import org.jetbrains.exposed.sql.Database

class DatabaseParticipateMeetingStorage(db: Database) : ParticipateMeetingUsecase.Storage {
    private val participantsStorage = ParticipantsStorage(db)
    override suspend fun addParticipant(participantId: UserId, meetingId: MeetingId) {
        participantsStorage.addParticipant(participantId, meetingId)
    }

    override suspend fun isParticipating(meetingId: MeetingId, userId: UserId): Boolean =
        participantsStorage.isParticipating(meetingId, userId)
}