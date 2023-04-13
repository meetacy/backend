package app.meetacy.backend.usecase.meetings.edit

import app.meetacy.backend.types.access.AccessIdentity
import app.meetacy.backend.types.datetime.Date
import app.meetacy.backend.types.file.FileIdentity
import app.meetacy.backend.types.location.Location
import app.meetacy.backend.types.meeting.IdMeeting
import app.meetacy.backend.types.meeting.MeetingIdentity
import app.meetacy.backend.usecase.types.*

class EditMeetingUsecase(
    private val storage: Storage,
    private val authRepository: AuthRepository,
    private val getMeetingsViewsRepository: GetMeetingsViewsRepository,
    private val filesRepository: FilesRepository,
    private val utf8Checker: Utf8Checker
) {

    sealed interface Result {
        object Success : Result
        object InvalidAccessIdentity : Result
        object InvalidUtf8String : Result
        object NullEditParameters : Result
        object InvalidMeetingId : Result
        object InvalidAvatarId : Result
    }

    suspend fun editMeeting(
        token: AccessIdentity,
        meetingId: MeetingIdentity,
        avatarId: FileIdentity?,
        deleteAvatar: Boolean,
        title: String?,
        description: String?,
        location: Location?,
        date: Date?,
        visibility: FullMeeting.Visibility?
    ): Result {
        if (title != null) if (!utf8Checker.checkString(title)) return Result.InvalidUtf8String
        if (description != null) if (!utf8Checker.checkString(description)) return Result.InvalidUtf8String
        if (avatarId != null) {
            if (!filesRepository.checkFile(avatarId)) return Result.InvalidAvatarId
        }
        val userId = authRepository.authorizeWithUserId(token) { return Result.InvalidAccessIdentity }

        val meetingCreator = getMeetingsViewsRepository
            .getMeetingsViewsOrNull(userId, listOf(meetingId.id))
            .first()
            ?.creator
            ?.identity
            ?: return Result.InvalidMeetingId
        if (userId != meetingCreator.userId) {
            return Result.InvalidAccessIdentity
        }
        if (listOf(avatarId, title, description, location, date, visibility).all { it == null }) {
            return Result.NullEditParameters
        }

        storage.editMeeting(
            meetingId.id,
            avatarId,
            deleteAvatar,
            title,
            description,
            location,
            date,
            visibility
        )
        return Result.Success
    }

    interface Storage {
        suspend fun editMeeting(
            idMeeting: IdMeeting,
            avatarId: FileIdentity?,
            deleteAvatar: Boolean,
            title: String?,
            description: String?,
            location: Location?,
            date: Date?,
            visibility: FullMeeting.Visibility?
        )
    }
}
