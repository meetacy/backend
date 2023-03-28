package app.meetacy.backend.usecase.meetings.participate

import app.meetacy.backend.types.AccessIdentity
import app.meetacy.backend.types.MeetingId
import app.meetacy.backend.types.MeetingIdentity
import app.meetacy.backend.types.UserId
import app.meetacy.backend.usecase.types.AuthRepository
import app.meetacy.backend.usecase.types.GetMeetingsViewsRepository
import app.meetacy.backend.usecase.types.authorizeWithUserId

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
            .getMeetingsViewsOrNull(userId, listOf(meetingIdentity.meetingId))
            .first()
            ?: return Result.MeetingNotFound

        if (meetingIdentity.accessHash != meeting.identity.accessHash)
            return Result.MeetingNotFound

        if(!storage.isParticipating(meetingIdentity.meetingId, userId))
            storage.addParticipant(userId, meetingIdentity.meetingId) else return Result.MeetingAlreadyParticipate

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