@file:Suppress("UNUSED_VARIABLE")

package app.meetacy.backend.infrastructure.database

import app.meetacy.backend.di.DI
import app.meetacy.backend.di.builder.DIBuilder
import app.meetacy.backend.di.dependency.Dependency
import app.meetacy.backend.infrastructure.database.auth.auth
import app.meetacy.backend.infrastructure.database.email.email
import app.meetacy.backend.infrastructure.database.files.files
import app.meetacy.backend.infrastructure.database.friends.friends
import app.meetacy.backend.infrastructure.database.invitations.invitations
import app.meetacy.backend.infrastructure.database.meetings.meetings
import app.meetacy.backend.infrastructure.database.notifications.notifications
import app.meetacy.backend.infrastructure.database.updates.updates
import app.meetacy.backend.infrastructure.database.users.users
import org.jetbrains.exposed.sql.Database

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
        val databaseUrl: String by getting
        val databaseUser: String by getting
        val databasePassword: String by getting

        Database.connect(
            url = databaseUrl,
            user = databaseUser,
            password = databasePassword
        )
    }
}
