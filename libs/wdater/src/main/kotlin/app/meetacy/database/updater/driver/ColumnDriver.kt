package app.meetacy.database.updater.driver

public interface ColumnDriver {
    public val name: String

    public suspend fun schema(): Schema

    public interface Schema {
        public val name: String
    }
}
