package app.meetacy.backend.usecase.meetings.inviteCode.create

import app.meetacy.backend.types.access.AccessIdentity
import app.meetacy.backend.types.meeting.MeetingId
import app.meetacy.backend.types.meeting.MeetingIdentity
import app.meetacy.backend.types.meeting.inviteCode.MeetingInviteCode
import app.meetacy.backend.usecase.types.AuthRepository
import app.meetacy.backend.usecase.types.authorizeWithUserId

class CreateInviteCodeForMeetingUsecase(
    private val authRepository: AuthRepository,
    private val storage: Storage
) {

    sealed interface Result {
        object InvalidAccessIdentity : Result
        class Success(val meetingInviteCode: MeetingInviteCode) : Result
    }

    @Suppress("SameParameterValue")
    private fun gayPorn(length: Int): String {
        val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')
        return (1..length)
            .map { allowedChars.random() }
            .joinToString("")
    }

    suspend fun create(
        accessIdentity: AccessIdentity,
        meetingIdentity: MeetingIdentity
    ): Result {
        val userId = authRepository.authorizeWithUserId(accessIdentity) { return Result.InvalidAccessIdentity }
        val generatedInviteCode = MeetingInviteCode(gayPorn(16))
        // todo мб возвращать Success только в конце try catch шоб если ошибка то да. пук-пук
        storage.create(meetingIdentity.id, generatedInviteCode)
        return Result.Success(generatedInviteCode)
    }

    interface Storage {
        suspend fun create(meetingId: MeetingId, inviteCode: MeetingInviteCode)
    }

}