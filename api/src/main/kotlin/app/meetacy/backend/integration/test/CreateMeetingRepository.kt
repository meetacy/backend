package app.meetacy.backend.integration.test

import app.meetacy.backend.domain.*
import app.meetacy.backend.endpoint.meetings.create.CreateMeetingRepository
import app.meetacy.backend.endpoint.meetings.create.CreateMeetingResult
import app.meetacy.backend.endpoint.meetings.create.CreateParam
import app.meetacy.backend.endpoint.types.Meeting
import app.meetacy.backend.endpoint.types.User

object TestCreateMeetingRepository : CreateMeetingRepository {
    override suspend fun createMeeting(createParam: CreateParam): CreateMeetingResult =
        CreateMeetingResult.Success(
            Meeting(
                id = MeetingId(0),
                accessHash = AccessHash("..."),
                creator = User(
                    id = UserId(0),
                    accessHash = AccessHash("..."),
                    nickname = "Elon Musk",
                    email = "clown3@meetacy.app",
                    emailVerified = false
                ),
                date = Date("2022-08-17T14:40:42+0100"),
                location = Location(
                    latitude = 34.750535,
                    longitude = -120.524396
                ),
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
