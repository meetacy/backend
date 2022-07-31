package app.meetacy.backend.mock.storage

import app.meetacy.backend.domain.MeetingId
import app.meetacy.backend.domain.UserId

object ParticipantsStorage {
    private val data: MutableList<MockParticipant> = mutableListOf()

    fun addParticipant(meetingId: MeetingId, userId: UserId) {
        data += MockParticipant(meetingId, userId)
    }

    fun participantsCount(meetingId: MeetingId) =
        data.count { it.meetingId == meetingId }

    fun isParticipating(meetingId: MeetingId, userId: UserId) =
        data.any { it.meetingId == meetingId && it.userId == userId }

}
