package app.meetacy.backend.database.integration.meetings.inviteCode.create

import app.meetacy.backend.database.meetings.invites.MeetingInviteCodesTable
import app.meetacy.backend.types.meeting.MeetingId
import app.meetacy.backend.types.meeting.inviteCode.MeetingInviteCode
import app.meetacy.backend.usecase.meetings.inviteCode.create.CreateInviteCodeForMeetingUsecase
import org.jetbrains.exposed.sql.Database

class DatabaseCreateInviteCodeForMeetingRepository(private val db: Database) : CreateInviteCodeForMeetingUsecase.Storage {
    val meetingInviteCodesTable = MeetingInviteCodesTable(db)

    override suspend fun create(meetingId: MeetingId, inviteCode: MeetingInviteCode) {
        meetingInviteCodesTable.create(meetingId, inviteCode)
    }

}