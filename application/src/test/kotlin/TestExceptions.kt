import app.meetacy.sdk.MeetacyApi
import app.meetacy.sdk.exception.MeetacyConnectionException
import app.meetacy.sdk.exception.MeetacyUnauthorizedException
import app.meetacy.sdk.types.annotation.UnsafeConstructor
import app.meetacy.sdk.types.auth.Token
import app.meetacy.sdk.types.url.url
import io.ktor.client.*
import io.ktor.client.engine.mock.*
import io.ktor.utils.io.errors.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlin.test.Test

@OptIn(ExperimentalCoroutinesApi::class)
class TestExceptions {

    @Test
    fun `authorization test`() = runTestServer {
        val failure = runCatching {
            @OptIn(UnsafeConstructor::class)
            testApi.getMe(Token("1:_INVALID_TOKEN_qD3Z0uM0iqE7g1J8VxkuzGe0CAXXDyHdfUGmj2xBPhuMYGFcHVawNvrK1KB9F9rgoeLa8Go2lqPDnzKYJg4EFbJUyQ6qu6P3iGg5Ytl4w1tpO1nja1aFxNtneq07uFERxSSsR7jd5YAe1Y0urlx9KDKxoQdIdGVvWGuc7dv3IStQUCZQziSmzjuxrVrUF9ywvg1bM8GiR2TU5nUItRPDhDyebeMzQcC7vwRYTdbUIIh4dYX4y"))
        }.exceptionOrNull()

        require(failure is MeetacyUnauthorizedException)
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
