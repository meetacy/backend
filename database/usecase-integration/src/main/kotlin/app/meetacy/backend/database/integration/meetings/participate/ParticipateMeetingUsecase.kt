package app.meetacy.backend.database.integration.meetings.participate

import app.meetacy.backend.database.meetings.ParticipantsTable
import app.meetacy.backend.types.meeting.IdMeeting
import app.meetacy.backend.types.user.UserId
import app.meetacy.backend.usecase.meetings.participate.ParticipateMeetingUsecase
import org.jetbrains.exposed.sql.Database

class DatabaseParticipateMeetingStorage(db: Database) : ParticipateMeetingUsecase.Storage {
    private val participantsTable = ParticipantsTable(db)
    override suspend fun addParticipant(participantId: UserId, idMeeting: IdMeeting) {
        participantsTable.addParticipant(participantId, idMeeting)
    }

    override suspend fun isParticipating(idMeeting: IdMeeting, userId: UserId): Boolean =
        participantsTable.isParticipating(idMeeting, userId)
}