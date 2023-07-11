package app.meetacy.discord.webhook

interface DiscordWebhookApi {
    fun create(url: String): DiscordWebhook
}
