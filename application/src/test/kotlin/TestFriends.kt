import io.ktor.client.*
import kotlin.test.Test

class TestFriends {
    val api = MeetacyApi(
        baseUrl = "http://localhost:8080",
    )

    @Test
    fun `pagination test of friends`() {
        startTestEndpoints()
        val api = MeetacyApi(
            baseUrl = "http://localhost:8080",
        )

        val myApi = api.auth.generateAuthorizedApi(nickname = "Tester Account at ${System.currentTimeMillis()}")
        val me = myApi.getMe()

        val friends = List(15) {
            api.auth.generateAuthorizedApi(nickname = "Tester Friend at ${System.currentTimeMillis()} #$it")
        }.map { friendApi ->
            friendApi to friendApi.getMe()
        }

        for ((friendApi, friend) in friends) {
            myApi.friends.add(friend.id)
            friendApi.friends.add(me.id)
        }

        myApi.friends.flow(chunkSize = 2.amount).toList() // expect it to be 15 
    }
}
