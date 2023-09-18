
import app.meetacy.backend.runServer
import app.meetacy.sdk.MeetacyApi
import app.meetacy.sdk.meetings.AuthorizedMeetingsApi
import app.meetacy.sdk.types.annotation.UnstableApi
import app.meetacy.sdk.types.auth.Token
import app.meetacy.sdk.types.datetime.Date
import app.meetacy.sdk.types.location.Location
import app.meetacy.sdk.types.url.url
import app.meetacy.sdk.users.AuthorizedSelfUserRepository
import io.ktor.client.*
import io.ktor.client.plugins.logging.*
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import java.io.IOException
import java.net.ServerSocket

fun runTestServer(
    wait: Boolean = false,
    block: suspend TestScope.() -> Unit
) = runTest {
    bruteForcePort {
        runServer(
            port = it,
            databaseUrl = "jdbc:h2:mem:test_${testNum++};DB_CLOSE_DELAY=60000",
        ).start(wait)
        block()
    }
}

var port: Int = 1024
var testNum: UInt = 0U

@OptIn(UnstableApi::class)
val testApi get() = MeetacyApi(
    baseUrl = "http://localhost:$port".url,
    httpClient = HttpClient {
        Logging {
            level = LogLevel.NONE
//            level = LogLevel.ALL
        }
        expectSuccess = true
        developmentMode = true
    }
)

suspend fun generateTestAccount(
    postfix: String? = null
): AuthorizedSelfUserRepository {
    val newClient = testApi.auth.generateAuthorizedApi(
        nickname = listOfNotNull("Test Account", postfix)
            .joinToString(separator = " ")
    )

    return newClient.getMe()
}

val InvalidToken: Token = Token("1:_INVALID_TOKEN_qD3Z0uM0iqE7g1J8VxkuzGe0CAXXDyHdfUGmj2xBPhuMYGFcHVawNvrK1KB9F9rgoeLa8Go2lqPDnzKYJg4EFbJUyQ6qu6P3iGg5Ytl4w1tpO1nja1aFxNtneq07uFERxSSsR7jd5YAe1Y0urlx9KDKxoQdIdGVvWGuc7dv3IStQUCZQziSmzjuxrVrUF9ywvg1bM8GiR2TU5nUItRPDhDyebeMzQcC7vwRYTdbUIIh4dYX4y")

fun InvalidId(id: String): String = "$id:_INVALID_ID_qD3qD3Z0uM0iqE7g1J8VxkuzGe0CAXXDyHdfUGmj2xBPhuMYGFcHVawNvrK1KB9F9rgoeLa8Go2lqPDnzKYJg4EFbJUyQ6qu6P3iGg5Ytl4w1tpO1nja1aFxNtneq07uFERxSSsR7jd5YAe1Y0urlx9KDKxoQdIdGVvWGuc7dv3IStQUCZQziSmzjuxrVrUF9ywvg1bM8GiR2TU5nUItRPDhDyebeMzQcC7vwRYTdbUIIh4dYX4y"

suspend fun AuthorizedMeetingsApi.createTestMeeting(title: String = "Test Meeting") =
    create(
        title = title,
        date = Date.today(),
        location = Location.NullIsland
    )

private fun isPortFree(port: Int): Boolean {
    var serverSocket: ServerSocket? = null

    try {
        serverSocket = ServerSocket(port) // server sockets are way faster to start and fail than our server
    } catch (e: IOException) {
        return false
    } finally {
        serverSocket?.close()
    }

    return true
}

private inline fun bruteForcePort(block: (Int) -> Unit) {
    while (!isPortFree(port)) {
        port++
    }
    block(port)
}
