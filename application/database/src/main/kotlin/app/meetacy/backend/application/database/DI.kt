package app.meetacy.backend.application.database

import app.meetacy.backend.feature.auth.database.integration.auth
import app.meetacy.backend.application.database.email.email
import app.meetacy.backend.application.database.files.files
import app.meetacy.backend.application.database.friends.friends
import app.meetacy.backend.application.database.invitations.invitations
import app.meetacy.backend.application.database.meetings.meetings
import app.meetacy.backend.application.database.notifications.notifications
import app.meetacy.backend.application.database.updates.updates
import app.meetacy.backend.application.database.users.users
import app.meetacy.di.DI
import app.meetacy.di.builder.DIBuilder
import app.meetacy.di.dependency.Dependency
import org.jetbrains.exposed.sql.Database

val DI.databaseConfig: app.meetacy.backend.application.database.DatabaseConfig by Dependency

data class DatabaseConfig(
    val url: String,
    val user: String,
    val password: String
)

val DI.database: Database by Dependency

fun DIBuilder.database() {
    singleton()
    // storages

    auth()
    email()
    files()
    friends()
    invitations()
    meetings()
    notifications()
    updates()
    users()
}

private fun DIBuilder.singleton() {
    val database by singleton {
        with(databaseConfig) {
            Database.connect(
                url = url,
                user = user,
                password = password
            )
        }
    }
}
