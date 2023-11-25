package app.meetacy.backend.database

import app.meetacy.backend.feature.auth.database.TokensTable
import app.meetacy.backend.feature.email.database.ConfirmationTable
import app.meetacy.backend.feature.files.database.FilesTable
import app.meetacy.backend.feature.friends.database.friends.FriendsTable
import app.meetacy.backend.feature.friends.database.location.UsersLocationsTable
import app.meetacy.backend.feature.invitations.database.invitations.InvitationsTable
import app.meetacy.backend.feature.meetings.database.meetings.MeetingsTable
import app.meetacy.backend.feature.meetings.database.meetings.ParticipantsTable
import app.meetacy.backend.feature.notifications.database.LastReadNotificationsTable
import app.meetacy.backend.feature.notifications.database.NotificationsTable
import app.meetacy.backend.feature.telegram.database.TelegramAuthTable
import app.meetacy.backend.feature.updates.database.updates.UpdatesTable
import app.meetacy.backend.feature.users.database.users.UsersTable
import org.jetbrains.exposed.sql.Table

val tables: List<Table> = listOf(
    TokensTable,
    UsersTable,
    ConfirmationTable,
    FilesTable,
    InvitationsTable,
    UsersLocationsTable,
    MeetingsTable,
    ParticipantsTable,
    LastReadNotificationsTable,
    NotificationsTable,
    FriendsTable,
    UpdatesTable,
    TelegramAuthTable,
).apply {
    require(distinctBy { table -> table.tableName }.size == size) { "There were duplicates in `tables` list" }
}
