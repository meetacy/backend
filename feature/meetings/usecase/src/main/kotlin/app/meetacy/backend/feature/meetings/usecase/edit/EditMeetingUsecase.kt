package app.meetacy.backend.feature.meetings.usecase.edit

import app.meetacy.backend.types.access.AccessIdentity
import app.meetacy.backend.types.auth.AuthRepository
import app.meetacy.backend.types.auth.authorizeWithUserId
import app.meetacy.backend.types.datetime.Date
import app.meetacy.backend.types.files.FileId
import app.meetacy.backend.types.files.FileIdentity
import app.meetacy.backend.types.location.Location
import app.meetacy.backend.types.meetings.*
import app.meetacy.backend.types.optional.Optional
import app.meetacy.backend.types.optional.ifPresent
import app.meetacy.backend.types.optional.map
import app.meetacy.backend.types.utf8Checker.Utf8Checker
import app.meetacy.backend.types.files.FilesRepository
import app.meetacy.backend.types.files.checkFile

class EditMeetingUsecase(
    private val storage: Storage,
    private val authRepository: AuthRepository,
    private val getMeetingsViewsRepository: GetMeetingsViewsRepository,
    private val viewMeetingsRepository: ViewMeetingsRepository,
    private val filesRepository: FilesRepository,
    private val utf8Checker: Utf8Checker
) {

    sealed interface Result {
        class Success(val meeting: MeetingView) : Result
        data object InvalidAccessIdentity : Result
        data object InvalidUtf8String : Result
        data object NullEditParameters : Result
        data object InvalidMeetingIdentity : Result
        data object InvalidAvatarIdentity : Result
    }

    suspend fun editMeeting(
        token: AccessIdentity,
        meetingIdentity: MeetingIdentity,
        avatarIdentityOptional: Optional<FileIdentity?>,
        title: String?,
        description: String?,
        location: Location?,
        date: Date?,
        visibility: FullMeeting.Visibility?
    ): Result {
        if (title != null) if (!utf8Checker.checkString(title)) return Result.InvalidUtf8String
        if (description != null) if (!utf8Checker.checkString(description)) return Result.InvalidUtf8String

        avatarIdentityOptional.ifPresent { avatarIdentity ->
            avatarIdentity ?: return@ifPresent
            filesRepository.checkFile(avatarIdentity) { return Result.InvalidAvatarIdentity }
        }

        val userId = authRepository.authorizeWithUserId(token) { return Result.InvalidAccessIdentity }

        val meetingCreator = getMeetingsViewsRepository
            .getMeetingsViewsOrNull(userId, listOf(meetingIdentity.id))
            .first()
            ?.creator
            ?.identity
            ?: return Result.InvalidMeetingIdentity

        if (userId != meetingCreator.id) {
            return Result.InvalidAccessIdentity
        }
        if (listOf(avatarIdentityOptional, title, description, location, date, visibility).all { it == null }) {
            return Result.NullEditParameters
        }

        val fullMeeting = storage.editMeeting(
            meetingIdentity.id,
            avatarIdentityOptional.map { it?.id },
            title,
            description,
            location,
            date,
            visibility
        )

        val result = viewMeetingsRepository.viewMeetings(userId, listOf(fullMeeting)).first()

        return Result.Success(result)
    }

    interface Storage {
        suspend fun editMeeting(
            meetingId: MeetingId,
            avatarId: Optional<FileId?>,
            title: String?,
            description: String?,
            location: Location?,
            date: Date?,
            visibility: FullMeeting.Visibility?
        ): FullMeeting
    }
}
