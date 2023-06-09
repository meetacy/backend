package app.meetacy.database.updater.context

import app.meetacy.database.updater.driver.Transaction

public interface TransactionContext {
    public val transaction: Transaction

    public companion object {
        public fun of(transaction: Transaction): TransactionContext {
            return object : TransactionContext {
                override val transaction = transaction
            }
        }
    }
}
