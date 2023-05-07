@file:OptIn(ExperimentalCoroutinesApi::class)

import app.meetacy.sdk.types.location.Location
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
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
            friend.friends.location.stream(
                selfLocation = flow {
                    while (true) {
                        emit(expectedLocation)
                    }
                }
            ).collect()
        }

        val streamedLocation = self.friends.location.stream(emptyFlow()).first()
        job.cancelAndJoin()

        require(streamedLocation.location == expectedLocation)
    }
}
