@file:OptIn(ExperimentalCoroutinesApi::class)

import app.meetacy.sdk.exception.MeetacyUnauthorizedException
import app.meetacy.sdk.types.location.Location
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
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

        val streamedLocation = self.friends.location.flow(emptyFlow()).first()
        job.cancelAndJoin()

        require(streamedLocation.location == expectedLocation)
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
