package app.meetacy.backend.feature.auth.usecase.types

import app.meetacy.backend.types.meeting.MeetingId
import app.meetacy.backend.types.meeting.MeetingIdentity

interface CheckMeetingRepository {
    suspend fun checkMeetingIdentity(identity: MeetingIdentity): Boolean
}

suspend inline fun CheckMeetingRepository.checkMeetingIdentity(
    identity: MeetingIdentity,
    fallback: () -> Nothing
): MeetingId {
    if (!checkMeetingIdentity(identity)) fallback()
    return identity.id
}
