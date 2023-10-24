package app.meetacy.backend.run

import app.meetacy.backend.discord.reportFailure
import app.meetacy.discord.webhook.DiscordWebhook
import io.ktor.server.engine.*

class ProductionContext {
    var initialized: Boolean = false
}

suspend fun runProductionServer(
    webhook: DiscordWebhook?,
    block: suspend ProductionContext.() -> ApplicationEngine
): ApplicationEngine {
    val context = ProductionContext()

    try {
        if (webhook == null) {
            return block(context)
        } else reportFailure(webhook) {
            return block(context)
        }
    } catch (throwable: Throwable) {
        if (context.initialized) {
            return runProductionServer(webhook, block)
        } else {
            throw throwable
        }
    }
}
