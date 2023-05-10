@file:OptIn(ExperimentalCoroutinesApi::class)

import app.meetacy.sdk.types.location.Location
import app.meetacy.sdk.types.url.url
import io.ktor.client.*
import io.ktor.client.plugins.websocket.*
import io.rsocket.kotlin.ktor.client.RSocketSupport
import io.rsocket.kotlin.ktor.client.rSocket
import io.rsocket.kotlin.payload.buildPayload
import io.rsocket.kotlin.payload.data
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put
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
