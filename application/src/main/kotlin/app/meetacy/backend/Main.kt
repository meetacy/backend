package app.meetacy.backend

import app.meetacy.backend.application.database.DatabaseConfig
import app.meetacy.backend.application.endpoints.prepareEndpoints
import app.meetacy.backend.di.buildDI
import app.meetacy.backend.run.runProductionServer
import app.meetacy.backend.types.auth.telegram.SecretTelegramBotKey
import app.meetacy.backend.types.files.FileSize
import app.meetacy.discord.webhook.ktor.DiscordWebhook
import kotlinx.coroutines.coroutineScope
import java.io.File

suspend fun main(): Unit = coroutineScope {
    val port = System.getenv("PORT")?.toIntOrNull() ?: 8080
    val databaseUrl = System.getenv("DATABASE_URL")
    val databaseUser = System.getenv("DATABASE_USER") ?: ""
    val databasePassword = System.getenv("DATABASE_PASSWORD") ?: ""
    val filesBasePath = System.getenv("FILES_BASE_PATH") ?: File(
        /* parent = */ System.getenv("user.dir"),
        /* child = */ "files"
    ).apply { mkdirs() }.absolutePath
    val filesSizeLimit = System.getenv("FILES_SIZE_LIMIT")?.toLongOrNull() ?: (99L * 1024 * 1024)
    val useMockDatabase = System.getenv("USE_MOCK_DATABASE")?.toBoolean() ?: (databaseUrl == null)
    val discordWebhook = System.getenv("DISCORD_WEBHOOK_URL")?.let(::DiscordWebhook)
    val googlePlacesToken = System.getenv("GOOGLE_PLACES_TOKEN")
    val telegramAuthBotUsername = System.getenv("TELEGRAM_AUTH_BOT_USERNAME")
    val secretTelegramBotKey = System.getenv("SECRET_TELEGRAM_BOT_KEY")?.let(::SecretTelegramBotKey)

    val databaseConfig = if (useMockDatabase) {
        DatabaseConfig.Mock(port)
    } else {
        DatabaseConfig.Connection(
            url = databaseUrl ?: error("Please specify DATABASE_URL env variable, or set USE_MOCK_DATABASE to `true`"),
            user = databaseUser,
            password = databasePassword
        )
    }

    runProductionServer(discordWebhook) {
        val di = buildDI(
            port = port,
            coroutineScope = this@coroutineScope,
            databaseConfig = databaseConfig,
            fileBasePath = filesBasePath,
            fileSizeLimit = FileSize(filesSizeLimit),
            discordWebhook = discordWebhook,
            googlePlacesToken = googlePlacesToken,
            telegramAuthBotUsername = telegramAuthBotUsername,
            secretTelegramBotKey = secretTelegramBotKey
        )

        val server = prepareEndpoints(di)
        initialized = true
        server.start(wait = true)
    }
}

