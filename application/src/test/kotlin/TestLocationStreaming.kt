import app.meetacy.sdk.exception.MeetacyUnauthorizedException
import app.meetacy.sdk.types.location.Location
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlin.test.Test

class TestLocationStreaming {
    @Test
    fun `stream location`() = runTestServer {
        val expectedLocation = Location(
            latitude = 20.0,
            longitude = 23.0
        )

        val self = generateTestAccount()
        val friend = generateTestAccount(postfix = "Friend")

        self.friends.add(friend.id)
        friend.friends.add(self.id)

        val job = launch {
            friend.friends.location.flow(
                selfLocation = flow {
                    while (true) {
                        emit(expectedLocation)
                    }
                }
            ).collect()
        }

        val streamedLocation = self.friends.location.flow(flowOf(Location.NullIsland)).first()

        job.cancelAndJoin()
        require(streamedLocation.location == expectedLocation)
    }

    @Test
    fun `push location`() = runTestServer {
        val expectedLocation = Location.NullIsland

        val self = generateTestAccount()
        val friend = generateTestAccount()

        self.friends.add(friend.id)
        friend.friends.add(self.id)

        friend.friends.location.push(expectedLocation)
        val actualLocation = self.friends.location.flow(flowOf(Location.NullIsland)).first()

        require(actualLocation.location == expectedLocation)
    }

    @Test
    fun `test location streaming with invalid token`() = runTestServer {
        assertThrows<MeetacyUnauthorizedException> {
            testApi.friends.location.flow(
                InvalidToken,
                emptyFlow()
            ).collect()
        }
    }
}
