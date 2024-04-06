import app.meetacy.sdk.types.amount.amount
import app.meetacy.sdk.types.datetime.Date
import app.meetacy.sdk.types.location.Location
import app.meetacy.sdk.types.paging.data
import kotlin.test.Test

class TestNotifications {
    @Test
    fun `test subscription`() = runTestServer {
        val self = generateTestAccount()
        val other = generateTestAccount(postfix = "#2")

        val notifications = self.notifications.list(amount = 1.amount).data
        require(notifications.isEmpty())

        other.friends.add(self.id)

        val newNotifications = self.notifications.list(amount = 1.amount).data
        require(newNotifications.size == 1) { newNotifications }
    }

    @Test
    fun `test invitation`() = runTestServer {
        val self = generateTestAccount()
        val other = generateTestAccount(postfix = "#2")
        self.friends.add(other.id)

        val notifications = self.notifications.list(amount = 1.amount).data
        require(notifications.isEmpty())

        val meeting = other.meetings.create(
            title = "Test Invitations Notification",
            date = Date.today(),
            location = Location.NullIsland
        )

        other.invitations.create(
            usersIds = listOf(self.id),
            meetingId = meeting.id
        )

        val newNotifications = self.notifications.list(amount = 2.amount).data
        require(newNotifications.size == 1)
    }
}
