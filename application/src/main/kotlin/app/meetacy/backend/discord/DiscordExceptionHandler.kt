package app.meetacy.backend.discord

import app.meetacy.backend.application.endpoints.ExceptionHandler
import app.meetacy.di.builder.DIBuilder
import app.meetacy.discord.webhook.DiscordWebhook
import app.meetacy.discord.webhook.embed.DiscordEmbed
import app.meetacy.discord.webhook.execute
import io.ktor.server.application.*
import io.ktor.server.logging.*
import io.ktor.server.request.*

fun DIBuilder.exceptionsHandler() {
    val exceptionHandler by singleton<ExceptionHandler> {
        val discordWebhook: DiscordWebhook? by getting
        val webhook = discordWebhook
        if (webhook == null) {
            ExceptionHandler.Simple
        } else {
            DiscordExceptionHandler(webhook)
        }
    }
}

private class DiscordExceptionHandler(
    private val webhook: DiscordWebhook
) : ExceptionHandler {
    override suspend fun handle(call: ApplicationCall, throwable: Throwable) {
        println("Unhandled Exception: ${throwable.stackTraceToString()}")

        val title = "Unhandled Exception Occurred"

        val requestString = buildString {
            appendLine("```")
            append(call.request.toLogString())
            appendLine("```")

            appendLine("**Headers:**")

            appendLine("```http")
            for ((name, values) in call.request.headers.entries()) {
                val value: Any = if (values.size == 1) values.first() else values
                appendLine("$name: $value")
            }
            appendLine("```")

            append("**Body:**")
            appendLine("```json")
            appendLine(call.receive<ByteArray>().decodeToString())
            appendLine("```")
        }

        val description = throwable
            .prettyStacktrace()
            .take(n = 1900)

        val embedString = buildString {
            append(requestString)

            appendLine()

            appendLine("**Stacktrace:**")
            appendLine("```kotlin")
            append(description)
            appendLine("```")
        }

        val embed = DiscordEmbed(
            title = title,
            description = embedString,
            color = EmbedColorFailure
        )

        webhook.execute(embed)
    }
}
