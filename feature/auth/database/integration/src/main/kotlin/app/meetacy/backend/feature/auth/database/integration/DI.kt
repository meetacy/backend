package app.meetacy.backend.feature.auth.database.integration

import app.meetacy.backend.feature.auth.database.TokensStorage
import app.meetacy.di.builder.DIBuilder

fun DIBuilder.auth() {
    val tokensStorage by singleton { TokensStorage(db = get()) }
}
