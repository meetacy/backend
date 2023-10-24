package app.meetacy.backend.discord

import app.meetacy.discord.webhook.DiscordWebhook
import app.meetacy.discord.webhook.embed.DiscordEmbed
import app.meetacy.discord.webhook.execute

suspend inline fun <T> reportFailure(webhook: DiscordWebhook, block: () -> T): T {
    return try {
        block()
    } catch (throwable: Throwable) {
        val embed = DiscordEmbed(
            title = "УВАГА, ТРИВОГА. БЕКЕНД ВПАВ. Ось стектрейс:",
            description = throwable.stackTraceToString(),
            color = EmbedColorFailure
        )

        webhook.execute(embed)

        throw throwable
    }
}
