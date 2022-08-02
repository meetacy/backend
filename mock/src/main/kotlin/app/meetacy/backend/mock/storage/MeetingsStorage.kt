package app.meetacy.backend.mock.storage

import app.meetacy.backend.types.*

object MeetingsStorage {
    private val data: MutableList<MockMeeting> = mutableListOf()

    fun addMeeting(
        accessHash: AccessHash,
        creatorId: UserId,
        date: Date,
        location: Location,
        title: String?,
        description: String?
    ): MockMeeting {
        val id = MeetingId(data.size.toLong())
        val meeting = MockMeeting(
            id, accessHash, creatorId, date, location, title, description
        )
        data += meeting
        return meeting
    }

    fun getMeetings(idsList: List<MeetingId>): List<MockMeeting> =
        data.filter { it.id in idsList }
            .apply {
                require(size == idsList.size) { "Cannot find every meeting id ($idsList)" }
            }

    fun getMeetingOrNull(id: MeetingId): MockMeeting? =
        data.firstOrNull { it.id == id }
}
