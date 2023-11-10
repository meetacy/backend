import app.meetacy.sdk.MeetacyApi
import app.meetacy.sdk.production
import app.meetacy.sdk.types.annotation.UnstableApi
import app.meetacy.sdk.types.auth.Token
import app.meetacy.sdk.types.url.url
import io.ktor.client.HttpClient
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.time.Duration

class TestClass {
//    @Test
    fun test() = runTest(timeout = Duration.INFINITE) {
        val api = MeetacyApi(
            baseUrl = "http://localhost:8080".url,
            httpClient = HttpClient {
                developmentMode = true
            }
        )

        // в приложении после редиректа вызываем await
        val token = api.auth.telegram.await(
            temporalToken = Token("BubA6u8AU4yXkzqezmynRvUSzovQAOAmo88bSuMyCgyStTLZpvdJmROJSG3IzU2f5WkLNSpzjwkDcOZto43TIsgi6YtBBUz0QFYYM1svXjnOp1SNEMLkQWq4N7XCNb8ga4ZMm9JyCd1nta0i9GgsWQE9ZN7kB7XPhAaBA5DysaHu8PWDncvWekOguwQGn7jdmAf1nkt6IXgKYSjPowA795kRsuKSSCdEh8t0SlBdFvX50IPC0uBTFycATLg5rXPg")
        )

        // Он ждёт...
        // Телеграм бот получает клик и отправляет запрос на бек
        // Получили юзера с никнеймом Alex Sokol, кк в ТГ
        val authorized = api.authorized(token)

        println(authorized.getMe().data)
    }
}
