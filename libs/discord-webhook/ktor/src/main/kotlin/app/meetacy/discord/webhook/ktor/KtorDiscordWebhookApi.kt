package app.meetacy.discord.webhook.ktor

import app.meetacy.discord.webhook.DiscordWebhook
import app.meetacy.discord.webhook.DiscordWebhookApi
import io.ktor.client.*

val DiscordWebhookApi: DiscordWebhookApi = DiscordWebhookApi()

fun DiscordWebhookApi(httpClient: HttpClient = HttpClient()): DiscordWebhookApi = KtorDiscordWebhookApi(httpClient)

private class KtorDiscordWebhookApi(private val httpClient: HttpClient) : DiscordWebhookApi {
    override fun create(url: String): DiscordWebhook = DiscordWebhook(url, httpClient)
}
