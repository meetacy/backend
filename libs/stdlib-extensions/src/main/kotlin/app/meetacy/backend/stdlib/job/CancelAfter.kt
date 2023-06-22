package app.meetacy.backend.stdlib.job

import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelAndJoin

suspend inline fun <T> Job.cancelAfter(block: () -> T): T {
    return try {
        block()
    } finally {
        cancelAndJoin()
    }
}
