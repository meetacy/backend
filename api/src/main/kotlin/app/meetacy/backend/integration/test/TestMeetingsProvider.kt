package app.meetacy.backend.integration.test

import app.meetacy.backend.domain.Location
import app.meetacy.backend.domain.MeetingId
import app.meetacy.backend.domain.UserId
import app.meetacy.backend.endpoint.meetings.list.ListMeetingsResult
import app.meetacy.backend.endpoint.meetings.list.MeetingsProvider
import app.meetacy.backend.endpoint.types.User
import app.meetacy.backend.endpoint.types.Meeting

object TestMeetingsProvider : MeetingsProvider {
    override suspend fun getList(accessToken: String): ListMeetingsResult =
        ListMeetingsResult.Success(
            meetings = listOf(
                Meeting(
                    id = MeetingId(0),
                    accessHash = "...",
                    creator = User(
                        id = UserId(0),
                        accessHash = "...",
                        nickname = "Elon Musk",,
                    ),
                    date = "2022-08-17T14:40:42+0100",
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
                    participantsCount = 1_000_000
                ),
                Meeting(
                    id = MeetingId(0),
                    accessHash = "...",
                    creator = User(
                        id = UserId(0),
                        accessHash = "...",
                        nickname = "Emma Watson",,
                    ),
                    date = "2023-08-17T14:40:42+0100",
                    location = Location(
                        latitude = 38.897518,
                        longitude = -77.036090
                    ),
                    title = "Meeting with an idol",
                    description = "Hi, friend. In this meeting, we will communicate and discuss pressing topics. " +
                            "You can always leave if you get bored or don't like something. Join our meeting - " +
                            "it will be fun.",
                    participantsCount = 1_200
                ),
                Meeting(
                    id = MeetingId(0),
                    accessHash = "...",
                    creator = User(
                        id = UserId(0),
                        accessHash = "...",
                        nickname = "Timothy Cookothy",,
                    ),
                    date = "2033-08-17T14:40:42+0100",
                    location = Location(
                        latitude = 35.077559,
                        longitude = -106.575164
                    ),
                    title = "Presentation Windows 12",
                    description = "With no assurances regarding Windows 11’s lifespan, " +
                            "it’s logical we’ll see a successor at some point. " +
                            "What’s more, there are suggestions it might not be far away," +
                            " with Microsoft rumoured to already be working on Windows 12 internally." +
                            " Here’s everything we know at this early stage.",
                    participantsCount = 4
                )
            )
        )
}
