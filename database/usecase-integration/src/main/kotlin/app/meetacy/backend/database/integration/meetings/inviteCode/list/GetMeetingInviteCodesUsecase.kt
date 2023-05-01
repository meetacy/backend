package app.meetacy.backend.database.integration.meetings.inviteCode.list

import app.meetacy.backend.database.meetings.invites.MeetingInviteCodesTable
import app.meetacy.backend.types.amount.Amount
import app.meetacy.backend.types.meeting.MeetingId
import app.meetacy.backend.types.meeting.inviteCode.MeetingInviteCode
import app.meetacy.backend.types.paging.PagingId
import app.meetacy.backend.types.paging.PagingResult
import app.meetacy.backend.usecase.meetings.inviteCode.list.GetMeetingInviteCodesUsecase
import org.jetbrains.exposed.sql.Database

class DatabaseGetMeetingInviteCodesStorage(db: Database) : GetMeetingInviteCodesUsecase.Storage {
    private val meetingInviteCodesTable = MeetingInviteCodesTable(db)


    override suspend fun getMeetingInviteCodes(
        meetingId: MeetingId,
        amount: Amount,
        pagingId: PagingId?
    ): PagingResult<List<MeetingInviteCode>> =
        meetingInviteCodesTable.getMeetingInviteCodes(meetingId, amount, pagingId)

//    override suspend fun getMeetingAllInviteCodes(meetingId: MeetingId): List<MeetingInviteCode> =
//        meetingInviteCodesTable.getMeetingAllInviteCodes(meetingId)

}