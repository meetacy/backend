package app.meetacy.backend.discord

import app.meetacy.backend.application.endpoints.ExceptionsHandler
import app.meetacy.di.builder.DIBuilder
import app.meetacy.discord.webhook.DiscordWebhook

fun DIBuilder.discord() {
    val exceptionsHandler by singleton<ExceptionsHandler> {
        val discordWebhook: DiscordWebhook? by getting
        val webhook = discordWebhook
        if (webhook == null) {
            ExceptionsHandler.NoOp
        } else {
            DiscordExceptionsHandler(webhook)
        }
    }
}
