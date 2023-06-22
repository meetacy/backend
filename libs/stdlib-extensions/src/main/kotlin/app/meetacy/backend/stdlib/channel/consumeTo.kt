package app.meetacy.backend.stdlib.channel

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.channels.consumeEach

suspend fun <T> ReceiveChannel<T>.transferTo(destination: SendChannel<T>) {
    consumeEach { element ->
        destination.send(element)
    }
}

suspend inline fun <T, R> ReceiveChannel<T>.transferTo(destination: SendChannel<R>, transform: (T) -> R) {
    consumeEach { element ->
        destination.send(transform(element))
    }
}
