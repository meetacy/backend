package app.meetacy.database.updater.driver

public interface Transaction {
    public suspend fun close()
}
