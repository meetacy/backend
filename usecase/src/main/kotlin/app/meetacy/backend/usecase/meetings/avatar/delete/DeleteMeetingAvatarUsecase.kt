package app.meetacy.backend.usecase.meetings.avatar.delete

import app.meetacy.backend.types.AccessIdentity
import app.meetacy.backend.types.FileIdentity
import app.meetacy.backend.types.MeetingIdentity
import app.meetacy.backend.usecase.types.*

class DeleteMeetingAvatarUsecase(
    private val authRepository: AuthRepository,
    private val filesRepository: FilesRepository,
    private val getMeetingsViewsRepository: GetMeetingsViewsRepository,
    private val storage: Storage
) {
    sealed interface Result {
        object Success : Result
        object MeetingNotFound : Result
        object InvalidIdentity : Result
        object InvalidAvatarIdentity : Result
    }

    suspend fun deleteAvatar(
        accessIdentity: AccessIdentity,
        meetingIdentity: MeetingIdentity,
        avatarIdentity: FileIdentity
    ): Result {
        authRepository.authorizeWithUserId(accessIdentity) { return Result.InvalidIdentity }
        filesRepository.authorizeWithFileId(avatarIdentity) { return Result.InvalidAvatarIdentity }

        val meeting = getMeetingsViewsRepository
            .getMeetingsViewsOrNull(accessIdentity.userId, listOf(meetingIdentity.meetingId))
            .first()
            ?: return Result.MeetingNotFound

        if (meeting.creator.identity.userId != accessIdentity.userId) return Result.InvalidIdentity
        if (meetingIdentity.accessHash != meeting.identity.accessHash) return Result.MeetingNotFound
        storage.deleteAvatar(meetingIdentity, avatarIdentity)

        return Result.Success
    }

    interface Storage {
        suspend fun deleteAvatar (meetingIdentity: MeetingIdentity, avatarIdentity: FileIdentity)
    }
}
