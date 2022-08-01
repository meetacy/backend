package app.meetacy.backend.usecase.meetings

import app.meetacy.backend.domain.MeetingId
import app.meetacy.backend.domain.UserId
import app.meetacy.backend.usecase.types.*

class ViewMeetingsUsecase(
    private val getUsersViewsRepository: GetUsersViewsRepository,
    private val storage: Storage
) {
    suspend fun viewMeetings(
        viewerId: UserId,
        meetings: List<FullMeeting>
    ): List<MeetingView> {
        val creatorIds: List<UserId> = meetings
            .map { meeting -> meeting.creatorId }

        val creators = getUsersViewsRepository
            .getUsersViews(viewerId, creatorIds)
            .iterator()

        val meetingIds = meetings
            .map { meeting -> meeting.id }

        val participants = storage
            .getParticipantsCount(meetingIds)
            .iterator()

        val participation = storage
            .getParticipations(viewerId, meetingIds)
            .iterator()

        return meetings.map { meeting ->
            return@map with (meeting) {
                MeetingView(
                    id = id,
                    accessHash = accessHash,
                    creator = creators.next(),
                    date = date,
                    location = location,
                    title = title,
                    description = description,
                    participantsCount = participants.next(),
                    isParticipating = participation.next()
                )
            }
        }
    }

    interface Storage {
        suspend fun getParticipantsCount(meetingIds: List<MeetingId>): List<Int>
        suspend fun getParticipations(viewerId: UserId, meetingIds: List<MeetingId>): List<Boolean>
    }
}