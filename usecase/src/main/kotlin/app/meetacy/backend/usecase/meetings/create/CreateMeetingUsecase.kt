package app.meetacy.backend.usecase.meetings.create

import app.meetacy.backend.types.*
import app.meetacy.backend.usecase.types.*

class CreateMeetingUsecase(
    private val hashGenerator: HashGenerator,
    private val storage: Storage,
    private val authRepository: AuthRepository,
    private val viewMeetingRepository: ViewMeetingRepository,
    private val utf8Checker: Utf8Checker
) {

    sealed interface Result {
        class Success(val meeting: MeetingView) : Result
        object TokenInvalid : Result
        object InvalidUtf8String : Result
    }

    suspend fun createMeeting(
        accessIdentity: AccessIdentity,
        title: String?,
        description: String?,
        date: Date,
        location: Location,
    ): Result {
        if (title != null) if (!utf8Checker.checkString(title)) return Result.InvalidUtf8String
        if (description != null) if (!utf8Checker.checkString(description)) return Result.InvalidUtf8String
        val creatorId = authRepository.authorizeWithUserId(accessIdentity) { return Result.TokenInvalid }
        val accessHash = AccessHash(hashGenerator.generate())
        val fullMeeting = storage.addMeeting(accessHash, creatorId, date, location, title, description)
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
            description: String?
        ): FullMeeting
    }
    interface ViewMeetingRepository {
        suspend fun viewMeeting(viewer: UserId, meeting: FullMeeting): MeetingView
    }
}