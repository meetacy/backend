import app.meetacy.sdk.exception.MeetacyUnauthorizedException
import app.meetacy.sdk.types.notification.Notification
import app.meetacy.sdk.types.update.Update
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlin.test.Test

class TestUpdates {
    @Test
    fun `test update notifications`() = runTestServer {
        runBlocking {
            val self = generateTestAccount()
            val friend = generateTestAccount(postfix = "#2")

            val job = async { self.updates.flow().first() }

            delay(500)
            friend.friends.add(self.id)

            val update = job.await().data

            require(update is Update.Notification)

            val notification = update.notification
            require(notification is Notification.Subscription)
            require(notification.subscriber.id == friend.id)
        }
    }

    @Test
    fun `test updates with invalid token`() = runTestServer {
        assertThrows<MeetacyUnauthorizedException> {
            testApi.updates.flow(InvalidToken).collect()
        }
    }
}
