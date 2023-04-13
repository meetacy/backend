package app.meetacy.backend.usecase.meetings.avatar.add

import app.meetacy.backend.types.access.AccessIdentity
import app.meetacy.backend.types.file.FileIdentity
import app.meetacy.backend.types.meeting.MeetingIdentity
import app.meetacy.backend.usecase.types.AuthRepository
import app.meetacy.backend.usecase.types.FilesRepository
import app.meetacy.backend.usecase.types.GetMeetingsViewsRepository
import app.meetacy.backend.usecase.types.authorizeWithUserId

class AddMeetingAvatarUsecase(
    private val authRepository: AuthRepository,
    private val filesRepository: FilesRepository,
    private val getMeetingsViewsRepository: GetMeetingsViewsRepository,
    private val storage: Storage
) {
    sealed interface Result {
        object Success : Result
        object MeetingNotFound : Result
        object InvalidIdentity : Result
        object InvalidFileIdentity : Result
    }

    suspend fun addAvatar(
        accessIdentity: AccessIdentity,
        meetingIdentity: MeetingIdentity,
        avatarIdentity: FileIdentity
    ): Result {
        authRepository.authorizeWithUserId(accessIdentity) { return Result.InvalidIdentity }
        if (!filesRepository.checkFile(avatarIdentity)) return Result.InvalidFileIdentity

        val meeting = getMeetingsViewsRepository
            .getMeetingsViewsOrNull(accessIdentity.userId, listOf(meetingIdentity.id))
            .first()
            ?: return Result.MeetingNotFound

        if (meeting.creator.identity.userId != accessIdentity.userId) return Result.InvalidIdentity
        if (meetingIdentity.accessHash != meeting.identity.accessHash) return Result.MeetingNotFound
        storage.addAvatar(meetingIdentity, avatarIdentity)

        return Result.Success
    }

    interface Storage {
        suspend fun addAvatar (meetingIdentity: MeetingIdentity, avatarIdentity: FileIdentity)
    }
}
