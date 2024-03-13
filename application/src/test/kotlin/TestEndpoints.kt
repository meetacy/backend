@file:OptIn(UnsafeConstructor::class)

import app.meetacy.backend.application.database.DatabaseConfig
import app.meetacy.backend.application.endpoints.prepareEndpoints
import app.meetacy.backend.di.buildDI
import app.meetacy.backend.types.auth.telegram.SecretTelegramBotKey
import app.meetacy.backend.types.files.FileSize
import app.meetacy.di.DI
import app.meetacy.google.maps.GooglePlacesTextSearch
import app.meetacy.sdk.MeetacyApi
import app.meetacy.sdk.meetings.AuthorizedMeetingsApi
import app.meetacy.sdk.types.annotation.UnsafeConstructor
import app.meetacy.sdk.types.annotation.UnstableApi
import app.meetacy.sdk.types.auth.Token
import app.meetacy.sdk.types.datetime.Date
import app.meetacy.sdk.types.location.Location
import app.meetacy.sdk.types.url.url
import app.meetacy.sdk.users.AuthorizedSelfUserDetailsRepository
import app.meetacy.sdk.users.AuthorizedSelfUserRepository
import io.ktor.client.HttpClient
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import java.io.File
import java.net.BindException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest

class TestServerContext(
    val testScope: CoroutineScope,
    val testApi: MeetacyApi
) : CoroutineScope by testScope

fun runTestServer(
    mockGooglePlacesSearch: GooglePlacesTextSearch = GooglePlacesTextSearch.NoOp,
    block: suspend TestServerContext.() -> Unit
) = runTest {
    bruteForcePort { port ->
        coroutineScope {
            val di = buildDI(
                port = port,
                coroutineScope = this,
                mockGooglePlacesSearch = mockGooglePlacesSearch
            )

            val fileBasePath: String by di.getting

            val context = TestServerContext(
                testScope = this,
                testApi = testApi(port)
            )

            try {
                val server = prepareEndpoints(di)
                server.start(wait = false)
                block(context)
                server.stop()
            } finally {
                File(fileBasePath).deleteRecursively()
            }
        }
    }
}

private fun buildDI(
    port: Int,
    coroutineScope: CoroutineScope,
    mockGooglePlacesSearch: GooglePlacesTextSearch?
): DI {
    val fileBasePath = File(
        /* parent = */ System.getenv("user.dir"),
        /* child = */ "files-$port-test"
    ).apply {
        mkdirs()
        deleteOnExit()
    }.absolutePath

    return buildDI(
        port = port,
        coroutineScope = coroutineScope,
        databaseConfig = DatabaseConfig.Mock(port),
        fileBasePath = fileBasePath,
        fileSizeLimit = FileSize(bytesSize = 99L * 1024 * 1024),
        discordWebhook = null,
        googlePlacesToken = null,
        mockGooglePlacesSearch = mockGooglePlacesSearch,
        telegramAuthBotUsername = null,
        secretTelegramBotKey = SecretTelegramBotKey("")
    )
}

private inline fun <T> bruteForcePort(
    range: IntRange = 20_000..30_000,
    block: (port: Int) -> T
): T {
    while (true) {
        try {
            val random = range.random()
            return block(random)
        } catch (_: BindException) {
        }
    }
}

@OptIn(UnstableApi::class)
fun testApi(port: Int) = MeetacyApi(
    baseUrl = "http://localhost:$port".url,
    httpClient = HttpClient {
        Logging {
//            level = LogLevel.NONE
            level = LogLevel.ALL
        }
        developmentMode = true
    }
)

suspend fun TestServerContext.generateTestAccount(
    postfix: String? = null
): AuthorizedSelfUserDetailsRepository {
    val newClient = testApi.auth.generate(
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
