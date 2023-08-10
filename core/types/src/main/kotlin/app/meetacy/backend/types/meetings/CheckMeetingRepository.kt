package app.meetacy.backend.types.meetings

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
