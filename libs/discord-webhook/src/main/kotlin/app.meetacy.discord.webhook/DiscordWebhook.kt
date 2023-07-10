package app.meetacy.discord.webhook

import app.meetacy.discord.webhook.embed.DiscordEmbed
import app.meetacy.discord.webhook.embed.DiscordEmbeds

interface DiscordWebhook {
    suspend fun execute(content: String)
    suspend fun execute(embeds: DiscordEmbeds)
}

@Suppress("DeprecatedCallableAddReplaceWith", "RedundantSuspendModifier", "UnusedReceiverParameter")
@Deprecated(message = "Specify at least content or one embed", level = DeprecationLevel.ERROR)
suspend fun DiscordWebhook.execute() {
    error("Cannot execute discord webhook without parameters")
}

suspend fun DiscordWebhook.execute(vararg embeds: DiscordEmbed) {
    return execute(DiscordEmbeds(embeds.toList()))
}
