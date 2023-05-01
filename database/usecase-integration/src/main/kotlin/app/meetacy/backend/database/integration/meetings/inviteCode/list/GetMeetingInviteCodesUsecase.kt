package app.meetacy.backend.database.integration.meetings.inviteCode.list

import app.meetacy.backend.database.meetings.invites.MeetingInviteCodesTable
import app.meetacy.backend.types.meeting.MeetingId
import app.meetacy.backend.types.meeting.inviteCode.MeetingInviteCode
import app.meetacy.backend.usecase.meetings.inviteCode.list.GetMeetingInviteCodesUsecase
import org.jetbrains.exposed.sql.Database

class DatabaseGetMeetingInviteCodesStorage(db: Database) : GetMeetingInviteCodesUsecase.Storage {
    private val meetingInviteCodesTable = MeetingInviteCodesTable(db)

    override suspend fun getMeetingInviteCodes(meetingId: MeetingId): List<MeetingInviteCode> =
        meetingInviteCodesTable.getMeetingInviteCodes(meetingId)

}