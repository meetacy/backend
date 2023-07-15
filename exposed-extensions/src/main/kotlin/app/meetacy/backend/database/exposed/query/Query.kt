package app.meetacy.backend.database.exposed.query

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.Query
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import kotlin.coroutines.CoroutineContext

fun Query.wrapTransactionAsFlow(
    db: Database,
    context: CoroutineContext = Dispatchers.IO
): Flow<ResultRow> = channelFlow {
    newSuspendedTransaction(context, db) {
        for (element in this@wrapTransactionAsFlow) {
            send(element)
        }
    }
}
