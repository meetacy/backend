@file:Suppress("NAME_SHADOWING")

package app.meetacy.backend.di

import app.meetacy.backend.application.database.DatabaseConfig
import app.meetacy.backend.application.database.database
import app.meetacy.backend.application.usecase.usecase
import app.meetacy.backend.discord.discord
import app.meetacy.backend.types.files.FileSize
import app.meetacy.backend.types.integration.types
import app.meetacy.di.builder.di
import app.meetacy.discord.webhook.DiscordWebhook

fun buildDI(
    port: Int,
    databaseConfig: DatabaseConfig,
    fileBasePath: String,
    fileSizeLimit: FileSize,
    discordWebhook: DiscordWebhook?,
    telegramAuthBotUsername: String?
) = di(checkDependencies = false) {
    val port by constant(port)
    val databaseConfig by constant(databaseConfig)
    val fileBasePath by constant(fileBasePath)
    val fileSizeLimit by constant(fileSizeLimit)
    val discordWebhook by constant(discordWebhook)
    val telegramAuthBotUsername by constant(telegramAuthBotUsername)

    usecase()
    database()
    types()
    discord()
}
