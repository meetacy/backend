import app.meetacy.backend.endpoint.auth.email.confirm.ConfirmParams
import app.meetacy.backend.endpoint.auth.email.link.LinkParameters
import app.meetacy.backend.integration.mock.startMockEndpoints
import app.meetacy.backend.mock.storage.TokensStorage
import app.meetacy.backend.mock.storage.UsersStorage
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json

suspend fun main() {
    var lastEmailText: String? = null

    startMockEndpoints(port = 8080, wait = false) { _, text ->
        lastEmailText = text
    }

    val httpClient = HttpClient(CIO) {
        install(ContentNegotiation) {
            json()
        }
    }

    UsersStorage.addUser(
        nickname = "y9san9",
        accessHash = "..."
    )

    TokensStorage.addToken(
        userId = 0,
        token = "testtoken"
    )

    println(UsersStorage.getUser(id = 0))

    httpClient.post("http://localhost:8080/auth/email/link") {
        contentType(ContentType.Application.Json)
        setBody(LinkParameters(email = "y9san9@gmail.com", accessToken = "testtoken"))
    }.bodyAsText()

    println(UsersStorage.getUser(id = 0))

    while (lastEmailText == null) continue

    val confirmHash = lastEmailText!!.substringAfterLast("=")

    println("Extracted confirm hash: $confirmHash")

    val result = httpClient.post("http://localhost:8080/auth/email/confirm") {
        contentType(ContentType.Application.Json)
        setBody(ConfirmParams(email = "y9san9@gmail.com", confirmHash))
    }.bodyAsText()

    println(result)

    println(UsersStorage.getUser(id = 0))
}
