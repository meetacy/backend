package app.meetacy.backend.test

import app.meetacy.backend.types.*
import app.meetacy.backend.endpoint.meetings.create.CreateMeetingRepository
import app.meetacy.backend.endpoint.meetings.create.CreateMeetingResult
import app.meetacy.backend.endpoint.meetings.create.CreateParam
import app.meetacy.backend.endpoint.types.Meeting
import app.meetacy.backend.endpoint.types.User
import app.meetacy.backend.types.serialization.serializable

object TestCreateMeetingRepository : CreateMeetingRepository {
    override suspend fun createMeeting(createParam: CreateParam): CreateMeetingResult =
        CreateMeetingResult.Success(
            Meeting(
                id = MeetingId(0).serializable(),
                accessHash = AccessHash("...").serializable(),
                creator = User(
                    id = UserId(0).serializable(),
                    accessHash = AccessHash("...").serializable(),
                    nickname = "Elon Musk",
                    email = "clown3@meetacy.app",
                    emailVerified = false
                ),
                date = Date("2022-08-17T14:40:42+0100").serializable(),
                location = Location(
                    latitude = 34.750535,
                    longitude = -120.524396
                ).serializable(),
                title = "Launching a Rocket",
                description = "To launch, the rocket needs enough propellants so that" +
                        " the thrust pushing the rocket up is greater than the force " +
                        "of gravity pulling the rocket down. A rocket needs to speed " +
                        "up to at least 17,800 miles per hour—and fly above most of the " +
                        "atmosphere, in a curved path around Earth.",
                participantsCount = 1_000_000,
                isParticipating = true
            )
        )
}
