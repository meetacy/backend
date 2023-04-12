import app.meetacy.sdk.types.amount.amount
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlin.test.Test

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

        val actualFriends = self.friends.flow(chunkSize = 2.amount).toList().flatten()

        assert(actualFriends.size == friendsAmount)
    }
}
