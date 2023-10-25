package app.meetacy.backend.discord

import app.meetacy.backend.application.endpoints.ExceptionsHandler
import app.meetacy.di.builder.DIBuilder
import app.meetacy.discord.webhook.DiscordWebhook
import app.meetacy.discord.webhook.ktor.DiscordWebhook
import io.ktor.client.*

fun DIBuilder.discord() {
    val discordWebhook by singleton<DiscordWebhook> {
        val discordWebhookUrl: String by getting
        val httpClient: HttpClient by getting
        DiscordWebhook(discordWebhookUrl, httpClient)
    }

    val exceptionsHandler by singleton<ExceptionsHandler> {
        val discordWebhook: DiscordWebhook? by getting
        val webhook = discordWebhook
        if (webhook == null) {
            ExceptionsHandler.Simple
        } else {
            DiscordExceptionsHandler(webhook)
        }
    }
}
