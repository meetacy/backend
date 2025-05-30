package app.meetacy.backend.feature.meetings.usecase.create

import app.meetacy.backend.types.access.AccessHash
import app.meetacy.backend.types.access.AccessIdentity
import app.meetacy.backend.types.auth.AuthRepository
import app.meetacy.backend.types.auth.authorizeWithUserId
import app.meetacy.backend.types.datetime.Date
import app.meetacy.backend.types.files.FileId
import app.meetacy.backend.types.files.FileIdentity
import app.meetacy.backend.types.generator.AccessHashGenerator
import app.meetacy.backend.types.location.Location
import app.meetacy.backend.types.users.UserId
import app.meetacy.backend.types.utf8Checker.Utf8Checker
import app.meetacy.backend.types.files.FilesRepository
import app.meetacy.backend.types.files.checkFileIdentity
import app.meetacy.backend.types.meetings.*

class CreateMeetingUsecase(
    private val hashGenerator: AccessHashGenerator,
    private val storage: Storage,
    private val authRepository: AuthRepository,
    private val filesRepository: FilesRepository,
    private val viewMeetingsRepository: ViewMeetingsRepository,
    private val utf8Checker: Utf8Checker
) {

    sealed interface Result {
        data class Success(val meeting: MeetingView) : Result
        data object TokenInvalid : Result
        data object InvalidUtf8String : Result
        data object InvalidFileIdentity : Result
    }

    suspend fun createMeeting(
        token: AccessIdentity,
        title: MeetingTitle,
        description: MeetingDescription?,
        date: Date,
        location: Location,
        visibility: FullMeeting.Visibility,
        avatarIdentity: FileIdentity?
    ): Result {
        if (!utf8Checker.checkString(title.string)) return Result.InvalidUtf8String
        if (description != null) if (!utf8Checker.checkString(description.string)) return Result.InvalidUtf8String
        if (avatarIdentity != null && !filesRepository.checkFileIdentity(avatarIdentity)) {
            return Result.InvalidFileIdentity
        }

        val creatorId = authRepository.authorizeWithUserId(token) { return Result.TokenInvalid }
        val accessHash = AccessHash(hashGenerator.generate())
        val fullMeeting = storage.addMeeting(
            accessHash,
            creatorId,
            date,
            location,
            title,
            description,
            visibility,
            avatarIdentity?.id
        )

        storage.addParticipant(creatorId, fullMeeting.id, date)

        val meetingView = viewMeetingsRepository.viewMeeting(creatorId, fullMeeting)

        return Result.Success(meetingView)
    }

    interface Storage {
        suspend fun addMeeting(
            accessHash: AccessHash,
            creatorId: UserId,
            date: Date,
            location: Location,
            title: MeetingTitle,
            description: MeetingDescription?,
            visibility: FullMeeting.Visibility,
            avatarId: FileId?
        ): FullMeeting

        suspend fun addParticipant(participantId: UserId, meetingId: MeetingId, meetingDate: Date)
    }
}
