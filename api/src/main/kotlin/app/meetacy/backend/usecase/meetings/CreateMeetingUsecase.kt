package app.meetacy.backend.usecase.meetings

import app.meetacy.backend.domain.*
import app.meetacy.backend.usecase.types.*

class CreateMeetingUsecase(
    private val hashGenerator: HashGenerator,
    private val storage: Storage,
    private val authRepository: AuthRepository,
    private val viewMeetingRepository: ViewMeetingRepository
) {

    sealed interface Result {
        class Success(val meeting: MeetingView) : Result
        object TokenInvalid : Result
    }

    suspend fun createMeeting(
        accessToken: AccessToken,
        title: String?,
        description: String?,
        date: Date,
        location: Location,
    ): Result {
        val creatorId = authRepository.authorize(accessToken) { return Result.TokenInvalid }
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
        fun viewMeeting(viewer: UserId, meeting: FullMeeting): MeetingView
    }
}
