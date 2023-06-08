package app.meetacy.database.updater

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table.Dual.default
import org.jetbrains.exposed.sql.Transaction

public class MigrationContext(public val transaction: Transaction) {

    public fun Column<*>.create() {
        transaction.execInBatch(createStatement())
    }

    public fun <T> Column<T>.create(oneTimeInitial: T) {
        // first creating table with default value
        transaction.execInBatch(default(oneTimeInitial).createStatement())
        // then removing the default value
        transaction.execInBatch(modifyStatement())
    }

    public fun Column<*>.modify() {
        transaction.execInBatch(modifyStatement())
    }

    public fun Column<*>.drop() {
        transaction.execInBatch(dropStatement())
    }
}
