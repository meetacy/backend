import app.meetacy.backend.endpoint.versioning.ApiVersion
import app.meetacy.backend.endpoint.versioning.versioning
import io.ktor.client.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.content.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.cio.*
import io.ktor.server.engine.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlin.test.Test

class TestVersioning {
    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `test versioning api`() = runTest {
        val server = embeddedServer(CIO) {
            install(Routing)

            routing {
                get("/versioning") {
                    versioning {
                        v(ApiVersion.VersioningFeature) {
                            require(it.int == ApiVersion.VersioningFeature.int)
                            call.respond(HttpStatusCode.OK)
                        }
                        v(ApiVersion(1)) {
                            require(it.int == 1)
                            call.respond(HttpStatusCode.OK)
                        }
                    }
                }
            }
        }.start(wait = false)

        val client = HttpClient {
            expectSuccess = true
            Logging {
                level = LogLevel.ALL
            }
        }

        client.get("/versioning") {
            header(ApiVersion.Header, ApiVersion.VersioningFeature.int)
        }
        client.get("/versioning") {
            header(ApiVersion.Header, 1)
        }

        server.stop()
    }
}
