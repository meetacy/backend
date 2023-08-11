package app.meetacy.backend.usecase.meetings.create

import app.meetacy.backend.types.access.AccessHash
import app.meetacy.backend.types.access.AccessIdentity
import app.meetacy.backend.types.auth.AuthRepository
import app.meetacy.backend.types.auth.authorizeWithUserId
import app.meetacy.backend.types.datetime.Date
import app.meetacy.backend.types.file.FileId
import app.meetacy.backend.types.file.FileIdentity
import app.meetacy.backend.types.generator.AccessHashGenerator
import app.meetacy.backend.types.location.Location
import app.meetacy.backend.types.meetings.FullMeeting
import app.meetacy.backend.types.meetings.MeetingId
import app.meetacy.backend.types.meetings.MeetingView
import app.meetacy.backend.types.users.UserId
import app.meetacy.backend.types.utf8Checker.Utf8Checker
import app.meetacy.backend.usecase.types.FilesRepository
import app.meetacy.backend.usecase.types.checkFileIdentity

class CreateMeetingUsecase(
    private val hashGenerator: AccessHashGenerator,
    private val storage: Storage,
    private val authRepository: AuthRepository,
    private val filesRepository: FilesRepository,
    private val viewMeetingRepository: ViewMeetingRepository,
    private val utf8Checker: Utf8Checker
) {

    sealed interface Result {
        class Success(val meeting: MeetingView) : Result
        object TokenInvalid : Result
        object InvalidUtf8String : Result
        object InvalidFileIdentity : Result
    }

    suspend fun createMeeting(
        token: AccessIdentity,
        title: String?,
        description: String?,
        date: Date,
        location: Location,
        visibility: FullMeeting.Visibility,
        avatarIdentity: FileIdentity?
    ): Result {
        if (title != null) if (!utf8Checker.checkString(title)) return Result.InvalidUtf8String
        if (description != null) if (!utf8Checker.checkString(description)) return Result.InvalidUtf8String
        if (avatarIdentity != null && !filesRepository.checkFileIdentity(avatarIdentity)) {
            return Result.InvalidFileIdentity
        }

        val creatorId = authRepository.authorizeWithUserId(token) { return Result.TokenInvalid }

        val accessHash = AccessHash(hashGenerator.generate())

        val fullMeeting = storage.addMeeting(accessHash, creatorId, date, location, title, description, visibility, avatarIdentity?.id)
        storage.addParticipant(creatorId, fullMeeting.id)
        val meetingView = viewMeetingRepository.viewMeeting(creatorId, fullMeeting)

        return Result.Success(meetingView)
    }

    interface Storage {
        suspend fun addMeeting(
            accessHash: AccessHash,
            creatorId: UserId,
            date: Date,
            location: Location,
            title: String?,
            description: String?,
            visibility: FullMeeting.Visibility,
            avatarId: FileId?
        ): FullMeeting

        suspend fun addParticipant(participantId: UserId, meetingId: MeetingId)
    }
    interface ViewMeetingRepository {
        suspend fun viewMeeting(viewer: UserId, meeting: FullMeeting): MeetingView
    }
}
