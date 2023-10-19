package app.meetacy.backend.endpoint.ktor.rsocket

import io.ktor.server.application.*
import io.ktor.server.websocket.*
import io.rsocket.kotlin.Connection
import io.rsocket.kotlin.TransportApi
import io.rsocket.kotlin.ktor.server.RSocketSupport
import kotlinx.coroutines.CoroutineExceptionHandler

@OptIn(TransportApi::class)
fun Application.installRSocket(autoWebSocket: Boolean = true) {
    if (autoWebSocket) install(WebSockets)
    install(RSocketSupport) {
        server {
            interceptors {
                forConnection(::SafeConnection)
            }
        }
    }
}

@OptIn(TransportApi::class)
private class SafeConnection(
    val underlying: Connection
) : Connection by underlying {
    override val coroutineContext = underlying.coroutineContext + CoroutineExceptionHandler { _, _ -> }
}
