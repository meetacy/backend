@file:OptIn(ExperimentalCoroutinesApi::class)

import app.meetacy.sdk.types.amount.amount
import app.meetacy.sdk.types.paging.asFlow
import app.meetacy.sdk.types.paging.flatten
import app.meetacy.sdk.types.user.Relationship
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class TestFriends {

    @OptIn(ExperimentalCoroutinesApi::class)
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
}
