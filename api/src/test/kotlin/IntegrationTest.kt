import app.meetacy.backend.endpoint.Credentials
import app.meetacy.backend.endpoint.Status
import app.meetacy.backend.endpoint.auth.AuthProvider
import app.meetacy.backend.endpoint.startEndpoints
import app.meetacy.backend.integration.mockEndpoint.MockUsersProvider
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json

object TestAuthProvider : AuthProvider {
    override suspend fun authorize(credentials: Credentials): Status =
        Status(credentials.login == "test" && credentials.password == "test")
}

suspend fun main() {
    startEndpoints(8080, wait = false, TestAuthProvider, MockUsersProvider)

    val httpClient = HttpClient(CIO) {
        install(ContentNegotiation) {
            json()
        }
    }

    val status = httpClient.post("http://localhost:8080/auth") {
        contentType(ContentType.Application.Json)
        setBody(Credentials("test", "test"))
    }.body<Status>()

    println(status.status)
}
