import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.post
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.Serializable

@Serializable
data class User(
    val name: String,
    val surname: String
)

suspend fun main() {
    val httpClient = HttpClient(CIO) {
        install(ContentNegotiation) {
            json()
        }
    }

    val result = httpClient
        .post("http://localhost:8080/greeting") {
            contentType(ContentType.Application.Json)
        }
        .body<List<User>>()

    println(result)
}
