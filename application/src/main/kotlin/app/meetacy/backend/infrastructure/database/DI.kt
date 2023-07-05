@file:Suppress("UNUSED_VARIABLE")

package app.meetacy.backend.infrastructure.database

import app.meetacy.backend.di.DI
import app.meetacy.backend.di.builder.DIBuilder
import app.meetacy.backend.di.dependency.GettingDelegate
import org.jetbrains.exposed.sql.Database

val DI.database: Database by GettingDelegate

fun DIBuilder.database() {
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
