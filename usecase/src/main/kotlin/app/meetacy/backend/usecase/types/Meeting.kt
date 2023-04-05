package app.meetacy.backend.usecase.types

import app.meetacy.backend.types.datetime.DateOrTime
import app.meetacy.backend.types.file.FileIdentity
import app.meetacy.backend.types.location.Location
import app.meetacy.backend.types.meeting.MeetingId
import app.meetacy.backend.types.meeting.MeetingIdentity
import app.meetacy.backend.types.user.UserId
import kotlinx.coroutines.coroutineScope

data class FullMeeting(
    val identity: MeetingIdentity,
    val creatorId: UserId,
    val date: DateOrTime,
    val location: Location,
    val title: String?,
    val description: String?,
    val avatarIdentity: FileIdentity? = null
) {
    val id: MeetingId = identity.meetingId
}

data class MeetingView(
    val identity: MeetingIdentity,
    val creator: UserView,
    val date: DateOrTime,
    val location: Location,
    val title: String?,
    val description: String?,
    val participantsCount: Int,
    val previewParticipants: List<UserView>,
    val isParticipating: Boolean,
    val avatarIdentity: FileIdentity? = null,
) {
    val id: MeetingId = identity.meetingId
}
