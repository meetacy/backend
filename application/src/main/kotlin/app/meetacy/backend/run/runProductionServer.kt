package app.meetacy.backend.run

import app.meetacy.discord.webhook.ktor.failure.reportFailure

class ProductionContext {
    var initialized: Boolean = false
}

suspend fun runProductionServer(
    webhookUrl: String?,
    block: suspend ProductionContext.() -> Unit
) {
    val context = ProductionContext()

    try {
        if (webhookUrl == null) {
            block(context)
        } else reportFailure(webhookUrl) {
            block(context)
        }
    } catch (throwable: Throwable) {
        if (context.initialized) {
            runProductionServer(webhookUrl, block)
        }
    }
}
