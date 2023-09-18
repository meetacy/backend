package app.meetacy.backend.types.meetings

fun interface MeetingExistsRepository {
    suspend fun meetingExists(identity: MeetingIdentity): Boolean
}

suspend inline fun MeetingExistsRepository.meetingExists(
    identity: MeetingIdentity,
    fallback: () -> Nothing
): MeetingId {
    if (!meetingExists(identity)) fallback()
    return identity.id
}
