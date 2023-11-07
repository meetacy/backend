package app.meetacy.backend.discord

import app.meetacy.di.builder.DIBuilder
import app.meetacy.discord.webhook.DiscordWebhook
import app.meetacy.discord.webhook.ktor.DiscordWebhook
import io.ktor.client.*

fun DIBuilder.discordWebhook() {
    val discordWebhook by singleton<DiscordWebhook> {
        val discordWebhookUrl: String by getting
        val httpClient: HttpClient by getting
        DiscordWebhook(discordWebhookUrl, httpClient)
    }
}
