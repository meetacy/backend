package app.meetacy.backend.discord

import app.meetacy.di.builder.DIBuilder

fun DIBuilder.discord() {
    exceptionsHandler()
    discordWebhook()
}
