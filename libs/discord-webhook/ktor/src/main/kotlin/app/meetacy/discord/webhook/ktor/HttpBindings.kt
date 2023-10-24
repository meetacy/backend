package app.meetacy.discord.webhook.ktor

import app.meetacy.discord.webhook.embed.DiscordEmbeds
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.*

internal class HttpBindings(client: HttpClient) {

    private val client: HttpClient = client.config {
        install(ContentNegotiation) {
            json()
        }
    }

    suspend fun execute(
        url: String,
        wait: Boolean? = true,
        threadId: String? = null,
        content: String? = null,
        username: String? = null,
        avatarUrl: String? = null,
        tts: Boolean? = null,
        embeds: DiscordEmbeds? = null
    ) {
        client.post(url) {
            parameter("wait", wait)
            parameter("threadId", threadId)

            contentType(ContentType.Application.Json)

            val json = buildJsonObject {
                if (content != null) put("content", content)
                if (username != null) put("username", username)
                if (avatarUrl != null) put("avatarUrl", avatarUrl)
                if (tts != null) put("tts", tts)

                if (embeds == null) return@buildJsonObject

                val embedsArray = buildJsonArray {
                    for (embed in embeds.list) with (embed) {
                        addJsonObject {
                            put("type", "rich")
                            if (title != null) put("title", title)
                            if (description != null) put("description", description)
                            if (color != null) put("color", color)
                            if (this@with.url != null) put("url", this@with.url)
                            if (timestamp != null) put("timestamp", timestamp)
                        }
                    }
                }

                put("embeds", embedsArray)
            }

            setBody(json)
        }.bodyAsText()
    }
}
