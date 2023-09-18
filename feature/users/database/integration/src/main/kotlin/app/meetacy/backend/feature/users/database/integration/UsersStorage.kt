package app.meetacy.backend.feature.users.database.integration

import app.meetacy.backend.feature.users.database.users.UsersStorage
import app.meetacy.di.builder.DIBuilder
import org.jetbrains.exposed.sql.Database

internal fun DIBuilder.usersStorage() {
    val usersStorage by singleton {
        val database: Database by getting
        UsersStorage(database)
    }
}