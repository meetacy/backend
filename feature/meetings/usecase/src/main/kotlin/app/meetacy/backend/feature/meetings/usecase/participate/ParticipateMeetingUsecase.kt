package app.meetacy.backend.feature.meetings.usecase.participate

import app.meetacy.backend.types.access.AccessIdentity
import app.meetacy.backend.types.auth.AuthRepository
import app.meetacy.backend.types.auth.authorizeWithUserId
import app.meetacy.backend.types.meetings.GetMeetingsViewsRepository
import app.meetacy.backend.types.meetings.MeetingId
import app.meetacy.backend.types.meetings.MeetingIdentity
import app.meetacy.backend.types.users.UserId

class ParticipateMeetingUsecase(
    private val authRepository: AuthRepository,
    private val storage: Storage,
    private val getMeetingsViewsRepository: GetMeetingsViewsRepository
) {

    suspend fun participateMeeting(
        meetingIdentity: MeetingIdentity,
        accessIdentity: AccessIdentity
    ): Result {
        val userId = authRepository.authorizeWithUserId(accessIdentity) { return Result.TokenInvalid }

        val meeting = getMeetingsViewsRepository
            .getMeetingsViewsOrNull(userId, listOf(meetingIdentity.id))
            .first()
            ?: return Result.MeetingNotFound

        if (meetingIdentity.accessHash != meeting.identity.accessHash)
            return Result.MeetingNotFound

        if(!storage.isParticipating(meetingIdentity.id, userId)) {
            storage.addParticipant(userId, meetingIdentity.id)
        } else {
            return Result.MeetingAlreadyParticipate
        }

        return Result.Success
    }

    sealed interface Result {
        object Success : Result
        object TokenInvalid : Result
        object MeetingNotFound : Result
        object MeetingAlreadyParticipate : Result
    }

    interface Storage {
        suspend fun addParticipant(
            participantId: UserId,
            meetingId: MeetingId
        )

        suspend fun isParticipating(
            meetingId: MeetingId, userId: UserId
        ): Boolean
    }

}