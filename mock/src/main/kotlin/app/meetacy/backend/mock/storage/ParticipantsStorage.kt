package app.meetacy.backend.mock.storage

import app.meetacy.backend.types.MeetingId
import app.meetacy.backend.types.UserId

object ParticipantsStorage {
    private val data: MutableList<MockParticipant> = mutableListOf()

    fun addParticipant(meetingId: MeetingId, userId: UserId) {
        data += MockParticipant(meetingId, userId)
    }

    fun participantsCount(meetingId: MeetingId): Int =
        data.count { it.meetingId == meetingId }

    fun isParticipating(meetingId: MeetingId, userId: UserId): Boolean =
        data.any { it.meetingId == meetingId && it.userId == userId }

    fun getMeetingIds(userId: UserId): List<MeetingId> = data
        .filter { it.userId == userId }
        .map { it.meetingId }
}
