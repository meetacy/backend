package app.meetacy.backend.usecase.meetings.get

import app.meetacy.backend.types.amount.Amount
import app.meetacy.backend.types.meeting.MeetingId
import app.meetacy.backend.types.user.UserId
import app.meetacy.backend.types.amount.amount
import app.meetacy.backend.usecase.types.*

class ViewMeetingsUsecase(
    private val getUsersViewsRepository: GetUsersViewsRepository,
    private val storage: Storage
) {
    suspend fun viewMeetings(
        viewerId: UserId,
        meetings: List<FullMeeting>,
        randomParticipantsAmount: Amount = 5.amount
    ): List<MeetingView> {
        val creatorIds: List<UserId> = meetings
            .map { meeting -> meeting.creatorId }

        val creators = getUsersViewsRepository
            .getUsersViews(viewerId, creatorIds)
            .iterator()

        val meetingIds = meetings
            .map { meeting -> meeting.id }

        val participantsCount = storage
            .getParticipantsCount(meetingIds)
            .iterator()

        val participation = storage
            .getIsParticipates(viewerId, meetingIds)
            .iterator()

        val firstParticipants = storage
            .getFirstParticipants(
                limit = randomParticipantsAmount,
                meetingIds = meetingIds
            )

        val randomParticipantsFlat = getUsersViewsRepository
            .getUsersViews(viewerId, firstParticipants.flatten())
            .iterator()

        val randomParticipants = firstParticipants.map { participantsIds ->
            participantsIds.map { randomParticipantsFlat.next() }
        }.iterator()

        return meetings.map { meeting ->
            return@map with (meeting) {
                MeetingView(
                    identity = identity,
                    creator = creators.next(),
                    date = date,
                    location = location,
                    title = title,
                    description = description,
                    participantsCount = participantsCount.next(),
                    previewParticipants = randomParticipants.next(),
                    isParticipating = participation.next(),
                    avatarIdentity = avatarIdentity
                )
            }
        }
    }

    interface Storage {
        suspend fun getParticipantsCount(meetingIds: List<MeetingId>): List<Int>
        suspend fun getIsParticipates(viewerId: UserId, meetingIds: List<MeetingId>): List<Boolean>
        suspend fun getFirstParticipants(limit: Amount, meetingIds: List<MeetingId>): List<List<UserId>>
    }
}
