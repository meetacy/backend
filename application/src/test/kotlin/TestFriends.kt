import app.meetacy.sdk.types.amount.amount
import app.meetacy.sdk.types.paging.asFlow
import app.meetacy.sdk.types.paging.data
import app.meetacy.sdk.types.paging.flatten
import app.meetacy.sdk.types.user.Relationship
import kotlinx.coroutines.flow.toList
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class TestFriends {

    @Test
    fun `pagination test of friends`() = runTestServer {
        val friendsAmount = (0..20).random()

        println("Testing with friendsAmount: $friendsAmount")

        val self = generateTestAccount(
            postfix = "at ${System.currentTimeMillis()}"
        )

        val friends = List(friendsAmount) {
            generateTestAccount(postfix = "Friend at ${System.currentTimeMillis()} #$it")
        }

        for (friend in friends) {
            self.friends.add(friend.id)
            friend.friends.add(self.id)
        }

        val actualFriends = self.friends
            .paging(chunkSize = 2.amount).flatten()
            .asFlow().toList()

        assert(actualFriends.size == friendsAmount)
    }

    @Test
    fun `test if self user does not have relationship property`() = runTestServer {
        val self = generateTestAccount()
        val user = self.api.users.get(self.id)

        assertEquals(user.id, self.id)
        assertNull(user.relationship)
    }

    @Test
    fun `test relationship property`() = runTestServer {
        val self = generateTestAccount()
        val friend = generateTestAccount()
        assert(self.users.get(friend.id).relationship == Relationship.None)

        self.friends.base.add(self.token, friend.id)
        assert(self.users.get(friend.id).relationship == Relationship.Subscription)
        assert(friend.users.get(self.id).relationship == Relationship.Subscriber)

        friend.friends.add(self.id)
        assert(self.users.get(friend.id).relationship == Relationship.Friend)
        assert(friend.users.get(self.id).relationship == Relationship.Friend)
    }

    @Test
    fun `test get self subscriptions`() = runTestServer {
        val self = generateTestAccount()
        val friend = generateTestAccount("Friend")

        self.friends.add(friend.id)
        val pagingSubscriptions = self.friends.subscriptions.list(5.amount)
        assert(pagingSubscriptions.data.first().id == friend.id)
    }

    @Test
    fun `test get self subscribers`() = runTestServer {
        val self = generateTestAccount()
        val friend = generateTestAccount("Friend")

        friend.friends.add(self.id)
        val pagingSubscriptions = self.friends.subscribers.list(5.amount)
        assert(pagingSubscriptions.data.first().id == friend.id)
    }

    @Test
    fun `test get user subscriptions`() = runTestServer {
        val self = generateTestAccount()
        val friend = generateTestAccount("Friend")

        self.friends.add(friend.id)
        val pagingSubscriptions = friend.friends.subscriptions.list(1.amount, userId = self.id)
        assert(pagingSubscriptions.data.first().id == friend.id)
    }

    @Test
    fun `test get user subscribers`() = runTestServer {
        val self = generateTestAccount()
        val friend = generateTestAccount("Friend")

        friend.friends.add(self.id)
        val pagingSubscriptions = friend.friends.subscribers.list(1.amount, userId = self.id)
        assert(pagingSubscriptions.data.first().id == friend.id)
    }
}
