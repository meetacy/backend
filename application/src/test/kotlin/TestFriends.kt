import app.meetacy.api.MeetacyApi
import app.meetacy.types.amount.amount
import io.ktor.client.*
import io.ktor.client.plugins.logging.*
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import kotlin.test.Test

class TestFriends {
    val api = MeetacyApi(baseUrl = "http://localhost:8080",)

    private val friendsAmount = (0..20).random()

    @Test
    fun `pagination test of friends`() = runTest {
        startTestEndpoints()

        println("Testing with friendsAmount: $friendsAmount")

        val api = MeetacyApi(
            baseUrl = "http://localhost:8080",
            httpClient = HttpClient {
                Logging {
                    level = LogLevel.NONE
                    level = LogLevel.ALL
                }
            }
        )

        val myApi = api.auth.generateAuthorizedApi(nickname = "Tester Account at ${System.currentTimeMillis()}")
        val me = myApi.getMe()

        val friends = List(friendsAmount) {
            api.auth.generateAuthorizedApi(nickname = "Tester Friend at ${System.currentTimeMillis()} #$it")
        }.map { friendApi ->
            friendApi to friendApi.getMe()
        }

        for ((friendApi, friend) in friends) {
            myApi.friends.add(friend.id)
            friendApi.friends.add(me.id)
        }

        val actualFriends = myApi.friends.flow(chunkSize = 2.amount).toList().flatten()

        assert(actualFriends.size == friendsAmount)
    }
}
