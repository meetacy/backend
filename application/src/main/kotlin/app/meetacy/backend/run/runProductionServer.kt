package app.meetacy.backend.run

import app.meetacy.discord.webhook.ktor.failure.reportFailure
import io.ktor.server.engine.*

class ProductionContext {
    var initialized: Boolean = false
}

suspend fun runProductionServer(
    webhookUrl: String?,
    block: suspend ProductionContext.() -> ApplicationEngine
): ApplicationEngine {
    val context = ProductionContext()

    try {
        if (webhookUrl == null) {
            return block(context)
        } else reportFailure(webhookUrl) {
            return block(context)
        }
    } catch (throwable: Throwable) {
        if (context.initialized) {
            return runProductionServer(webhookUrl, block)
        } else {
            throw throwable
        }
    }
}
