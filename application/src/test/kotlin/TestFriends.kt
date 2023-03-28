import app.meetacy.types.amount.amount
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import kotlin.test.Test

class TestFriends {

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `pagination test of friends`() = runTest {
        val friendsAmount = (0..20).random()

        startTestEndpoints()

        println("Testing with friendsAmount: $friendsAmount")

        val (myApi, me) = generateTestAccount(
            postfix = "at ${System.currentTimeMillis()}"
        )

        val friends = List(friendsAmount) {
            generateTestAccount(postfix = "Friend at ${System.currentTimeMillis()} #$it")
        }

        for ((friendApi, friend) in friends) {
            myApi.friends.add(friend.id)
            friendApi.friends.add(me.id)
        }

        val actualFriends = myApi.friends.flow(chunkSize = 2.amount).toList().flatten()

        assert(actualFriends.size == friendsAmount)
    }
}
