package app.meetacy.database.updater.driver

import app.meetacy.database.updater.context.TransactionContext

public interface DatabaseDriver {
    public suspend fun schema(): Schema

    public fun wrapTable(name: String): TableDriver

    context(TransactionContext)
    public suspend fun createTable(
        name: String,
        schema: TableDriver.Schema? = null
    ): TableDriver

    public suspend fun beginTransaction(): Transaction

    public interface Schema {
        public val tables: List<TableDriver.Schema>
    }
}

public suspend inline fun <T> DatabaseDriver.transaction(
    block: context(TransactionContext) () -> T
): T {
    val transaction = beginTransaction()
    return try {
        block(TransactionContext.of(transaction))
    } finally {
        transaction.close()
    }
}

context(TransactionContext)
public suspend fun DatabaseDriver.tableOrNull(name: String): TableDriver? {
    val tables = schema().tables
    if (tables.none { table -> table.name == name }) {
        return null
    }
    return wrapTable(name)
}

context(TransactionContext)
public suspend fun DatabaseDriver.table(name: String): TableDriver =
    tableOrNull(name) ?: error("There is no such table with this name")
