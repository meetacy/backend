import kotlinx.coroutines.*
import java.lang.IllegalStateException

suspend fun main() {
    try {
        val a = coroutineScope {
            try {
                launch {
                    delay(20_000)
                }
                launch {
                    throw IllegalStateException("NCSjasnckajsnascscsc")
                }
                launch {
                    delay(7_000)
                }
            } catch (_: Throwable) {

            }

            println("TEST")
            0
        }
    } catch (_: Throwable) {

    }
    
    println("TEST 2")

    awaitCancellation()
}

suspend fun withTimeout(
    timeout: Long,
    scope: CoroutineScope,
    block: suspend CoroutineScope.() -> Unit
) {
    val job = scope.launch { block() }
    delay(timeout)
    job.cancel()
}
