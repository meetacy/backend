package app.meetacy.backend.application.database

import app.meetacy.backend.feature.auth.database.integration.auth
import app.meetacy.backend.feature.email.database.integration.email
import app.meetacy.di.DI
import app.meetacy.di.builder.DIBuilder
import app.meetacy.di.dependency.Dependency
import org.jetbrains.exposed.sql.Database

val DI.databaseConfig: DatabaseConfig by Dependency

data class DatabaseConfig(
    val url: String,
    val user: String,
    val password: String
)

fun DIBuilder.database() {
    databaseSingleton()
    // storages

    auth()
    email()
}

private fun DIBuilder.databaseSingleton() {
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
