package app.meetacy.database.updater.driver

import app.meetacy.database.updater.context.TransactionContext

public interface TableDriver {
    public val name: String

    public suspend fun schema(): Schema

    context(TransactionContext)
    public suspend fun createColumn(name: String): ColumnDriver

    public fun wrapColumn(name: String): ColumnDriver

    public interface Schema {
        public val name: String
        public val columns: List<ColumnDriver.Schema>
    }
}
