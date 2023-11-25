package app.meetacy.backend.feature.telegram.database.integration

import app.meetacy.backend.feature.telegram.database.TelegramAuthStorage
import app.meetacy.di.builder.DIBuilder
import org.jetbrains.exposed.sql.Database

internal fun DIBuilder.telegramAuthStorage() {
    val telegramAuthStorage by singleton {
        val database: Database by getting
        TelegramAuthStorage(database)
    }
}
