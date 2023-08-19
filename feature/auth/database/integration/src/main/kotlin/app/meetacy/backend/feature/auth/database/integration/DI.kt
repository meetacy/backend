package app.meetacy.backend.feature.auth.database.integration

import app.meetacy.backend.feature.auth.database.TokensStorage
import app.meetacy.di.builder.DIBuilder
import org.jetbrains.exposed.sql.Database

fun DIBuilder.auth() {
    val tokensStorage by singleton {
        val database: Database by getting
        TokensStorage(database)
    }
}
