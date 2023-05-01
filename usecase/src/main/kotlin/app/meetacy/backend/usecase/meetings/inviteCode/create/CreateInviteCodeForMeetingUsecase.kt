package app.meetacy.backend.usecase.meetings.inviteCode.create

import app.meetacy.backend.types.access.AccessIdentity
import app.meetacy.backend.types.meeting.MeetingId
import app.meetacy.backend.types.meeting.MeetingIdentity
import app.meetacy.backend.types.meeting.inviteCode.MeetingInviteCode
import app.meetacy.backend.usecase.types.AuthRepository
import app.meetacy.backend.usecase.types.authorizeWithUserId

@Suppress("SameParameterValue")
private fun gayPorn(length: Int): String { // todo
    val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')
    return (1..length)
        .map { allowedChars.random() }
        .joinToString("")
}

class CreateInviteCodeForMeetingUsecase(
    private val authRepository: AuthRepository,
    private val storage: Storage
) {

    sealed interface Result {
        object InvalidAccessIdentity : Result
        class Success(val meetingInviteCode: MeetingInviteCode) : Result
    }

    @Suppress("SameParameterValue")
    private suspend fun generate(length: Int): MeetingInviteCode? {
        val maxAttempts = Int.SIZE_BITS.let {
            with(Double.SIZE_BYTES) {
                (
                        (
                                (
                                        (
                                                (
                                                        it shl (
                                                                this shl it
                                                                ) / this
                                                        ) or (
                                                        it shl (
                                                                this shl it
                                                                ) / (
                                                                this / 0b10
                                                                )
                                                        )
                                                ) or (
                                                it shr (
                                                        this shl it
                                                        ) / this
                                                )
                                        ) or (
                                        it shr 0x2
                                        )
                                ) shr 0b1
                        ) - 1e1.toInt() + 0b10
            }
        }; var attempts = 0; var inviteCode: MeetingInviteCode? = null
        while (inviteCode == null && attempts < maxAttempts) { attempts++; val a = MeetingInviteCode(gayPorn(length));
            if (storage.isInviteCodeFree(a)) { inviteCode = a } }
        return inviteCode
    }

    suspend fun create(
        accessIdentity: AccessIdentity,
        meetingIdentity: MeetingIdentity
    ): Result {
        val userId = authRepository.authorizeWithUserId(accessIdentity) { return Result.InvalidAccessIdentity }
        val generatedInviteCode = generate(16) ?: error("вахуи") // todo
        // todo мб возвращать Success только в конце try catch шоб если ошибка то да. пук-пук
        storage.create(meetingIdentity.id, generatedInviteCode)
        return Result.Success(generatedInviteCode)
    }

    interface Storage {
        suspend fun create(meetingId: MeetingId, inviteCode: MeetingInviteCode)
        suspend fun isInviteCodeFree(inviteCode: MeetingInviteCode): Boolean
    }

}