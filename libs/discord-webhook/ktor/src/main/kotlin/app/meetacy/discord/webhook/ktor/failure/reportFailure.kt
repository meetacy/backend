package app.meetacy.discord.webhook.ktor.failure

import app.meetacy.discord.webhook.failure.reportFailure
import app.meetacy.discord.webhook.ktor.DiscordWebhook

suspend inline fun <T> reportFailure(webhookUrl: String, block: () -> T): T {
    return reportFailure(DiscordWebhook(webhookUrl), block)
}
