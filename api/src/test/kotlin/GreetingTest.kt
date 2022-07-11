import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.*
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: Long,
    val username: String,
    val fullname: String?,
    val email: String,
    val bio: String

)

suspend fun main() {
    val httpClient = HttpClient(CIO) {
        install(ContentNegotiation) {
            json()
        }
    }

    val result = httpClient
        .get("http://localhost:8080") {
            contentType(ContentType.Application.Json)
        }
        .body<List<User>>()


    println(result)
}
