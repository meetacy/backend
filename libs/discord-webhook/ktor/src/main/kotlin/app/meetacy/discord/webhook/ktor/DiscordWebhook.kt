package app.meetacy.discord.webhook.ktor

import app.meetacy.discord.webhook.DiscordWebhook
import app.meetacy.discord.webhook.embed.DiscordEmbeds
import io.ktor.client.*

fun DiscordWebhook(url: String, httpClient: HttpClient = HttpClient()): DiscordWebhook {
    return KtorDiscordWebhook(url, httpClient)
}

private class KtorDiscordWebhook(
    private val url: String,
    httpClient: HttpClient
) : DiscordWebhook {

    private val bindings = HttpBindings(httpClient)

    override suspend fun execute(content: String) {
        bindings.execute(
            url = url,
            content = content
        )
    }

    override suspend fun execute(embeds: DiscordEmbeds) {
        bindings.execute(
            url = url,
            embeds = embeds
        )
    }
}
