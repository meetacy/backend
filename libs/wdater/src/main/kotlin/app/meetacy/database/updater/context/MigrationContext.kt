package app.meetacy.database.updater.context

import app.meetacy.database.updater.driver.DatabaseDriver
import app.meetacy.database.updater.driver.Transaction
import org.jetbrains.exposed.sql.Column

public interface MigrationContext {
    public val transaction: Transaction
    public val database: DatabaseDriver
}

public suspend fun Column<*>.create() {

}

//    context(Transaction)
//    public suspend fun Column<*>.create() {
//        database.table(table.tableName)
//    }
//
//    public fun <T> Column<T>.create(oneTimeInitial: T) {
//        // first creating table with default value
//        transaction.execInBatch(default(oneTimeInitial).createStatement())
//        // then removing the default value
//        transaction.execInBatch(modifyStatement())
//    }
//
//    public fun Column<*>.modify() {
//        transaction.execInBatch(modifyStatement())
//    }
//
//    public fun Column<*>.drop() {
//        transaction.execInBatch(dropStatement())
//    }
//}
