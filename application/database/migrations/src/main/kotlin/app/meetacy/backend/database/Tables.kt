package app.meetacy.backend.database

import app.meetacy.backend.feature.auth.database.TokensTable
import org.jetbrains.exposed.sql.Table

val tables: List<Table> = listOf(
    TokensTable,
//    UsersTable,
//    ConfirmationTable,
//    FilesTable,
//    InvitationsTable,
//    UsersLocationsTable,
//    MeetingsTable,
//    ParticipantsTable,
//    LastReadNotificationsTable,
//    NotificationsTable,
//    FriendsTable,
//    UpdatesTable
).apply {
    require(distinctBy { table -> table.tableName }.size == size) { "There were duplicates in `tables` list" }
}
