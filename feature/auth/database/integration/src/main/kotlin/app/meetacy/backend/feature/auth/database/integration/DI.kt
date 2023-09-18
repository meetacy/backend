package app.meetacy.backend.feature.auth.database.integration

import app.meetacy.di.builder.DIBuilder

fun DIBuilder.auth() {
    tokensStorage()
}
