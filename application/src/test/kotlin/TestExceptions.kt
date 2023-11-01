import app.meetacy.sdk.MeetacyApi
import app.meetacy.sdk.exception.MeetacyConnectionException
import app.meetacy.sdk.exception.MeetacyUnauthorizedException
import app.meetacy.sdk.types.url.url
import io.ktor.client.*
import io.ktor.client.engine.mock.*
import io.ktor.utils.io.errors.*
import kotlin.test.Test

class TestExceptions {

    @Test
    fun `authorization test`() = runTestServer {
        assertThrows<MeetacyUnauthorizedException> {
            testApi.getMe(InvalidToken)
        }
    }

    @Test
    fun `authorization test success`() = runTestServer {
        val me = generateTestAccount()
        val token = me.token
        require(token.string.isNotEmpty())
    }

    @Test
    fun `no internet test`() = runTestServer {
        val noNetworkApi = MeetacyApi(
            baseUrl = "http://localhost:8080".url,
            httpClient = HttpClient(MockEngine) {
                engine {
                    addHandler {
                        throw IOException("Cannot connect to internet")
                    }
                }
            }
        )

        val failure = runCatching {
            noNetworkApi.auth.generate(nickname = "Test")
        }.exceptionOrNull()

        require(failure is MeetacyConnectionException)
    }

}
