package app.meetacy.backend.database.integration.meetings.inviteCode.getMeeting

import app.meetacy.backend.database.meetings.invites.MeetingInviteCodesTable
import app.meetacy.backend.types.meeting.MeetingId
import app.meetacy.backend.types.meeting.inviteCode.MeetingInviteCode
import app.meetacy.backend.usecase.meetings.inviteCode.getMeeting.GetMeetingByInviteCodeUsecase
import org.jetbrains.exposed.sql.Database

class DatabaseGetMeetingByInviteCodeStorage(db: Database) : GetMeetingByInviteCodeUsecase.Storage {
    private val meetingInviteCodesTable = MeetingInviteCodesTable(db)

    override suspend fun getMeetingIdByInviteCode(inviteCode: MeetingInviteCode): MeetingId? =
        meetingInviteCodesTable.getMeetingId(inviteCode)
}